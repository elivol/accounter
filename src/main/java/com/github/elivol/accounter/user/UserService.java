package com.github.elivol.accounter.user;

import com.github.elivol.accounter.security.UserRole;
import com.github.elivol.accounter.security.UserRoleEntity;
import com.github.elivol.accounter.security.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String  USER_WITH_USERNAME_NOT_FOUND = "User with username %s not found";
    private static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User with email %s already exists";
    private static final String USER_WITH_USERNAME_ALREADY_EXISTS = "User with username %s already exists";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException(String.format(USER_WITH_USERNAME_NOT_FOUND, username))
        );
    }

    public User create(User user) {
        boolean userExistsByEmail = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean userExistsByUsername = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExistsByUsername) {
            throw new IllegalStateException(String.format(USER_WITH_USERNAME_ALREADY_EXISTS, user.getUsername()));
        }

        if (userExistsByEmail) {
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

    public User update(User user) {
        boolean userExistsByUsername = userRepository.findByUsername(user.getUsername()).isPresent();

        if (!userExistsByUsername) {
            throw new IllegalStateException(String.format(USER_WITH_USERNAME_NOT_FOUND, user.getUsername()));
        }

        return userRepository.save(user);
    }

    public UserProfile userProfile(User user) {
        return new UserProfile(
                user.getUsername(),
                user.getEmail(),
                user.getFullName()
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format(USER_WITH_USERNAME_NOT_FOUND, username))
                );
    }

}
