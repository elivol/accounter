package com.github.elivol.accounter.security.user;

import com.github.elivol.accounter.security.permission.UserRole;
import com.github.elivol.accounter.security.permission.UserRoleEntity;
import com.github.elivol.accounter.security.permission.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_WITH_ID_NOT_FOUND = "User whit id %d not found";
    private static final String USER_WITH_EMAIL_NOT_FOUND = "User whit email %s not found";
    private static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User whit email %s already exists";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException(String.format(USER_WITH_ID_NOT_FOUND, id))
        );
    }

    public User create(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException(String.format(USER_WITH_EMAIL_ALREADY_EXISTS, user.getEmail()));
        }

        userRoleRepository.findByRole(UserRole.USER)
                .ifPresentOrElse(
                        role -> user.getRoles().add(role),
                        () -> user.getRoles().add(new UserRoleEntity(UserRole.USER))
                );

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format(USER_WITH_EMAIL_NOT_FOUND, username))
                );
    }
}
