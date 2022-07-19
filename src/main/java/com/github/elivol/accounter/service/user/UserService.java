package com.github.elivol.accounter.service.user;

import com.github.elivol.accounter.dto.model.user.UserRegistrationRequest;
import com.github.elivol.accounter.exception.EntityAlreadyPresentException;
import com.github.elivol.accounter.exception.EntityNotFoundException;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.model.user.UserRoleModel;
import com.github.elivol.accounter.repository.UserRepository;
import com.github.elivol.accounter.repository.UserRoleRepository;
import com.github.elivol.accounter.security.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private static final String USER_WITH_USERNAME_NOT_FOUND = "User with username %s not found";
    private static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User with email %s already exists";
    private static final String USER_WITH_USERNAME_ALREADY_EXISTS = "User with username %s already exists";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format(USER_WITH_USERNAME_NOT_FOUND, username)));
    }

    @Transactional
    public User create(UserRegistrationRequest userRequest) {
        return createWithRoles(userRequest, Set.of(new UserRoleModel(UserRole.USER)));
    }

    @Transactional
    public User createWithRoles(UserRegistrationRequest userRequest, Set<UserRoleModel> roles) {

        // check for user
        User user = new User(
                userRequest.getUsername(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getFullName());

        boolean userExistsByEmail = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean userExistsByUsername = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExistsByUsername) {
            throw new EntityAlreadyPresentException(String.format(USER_WITH_USERNAME_ALREADY_EXISTS, user.getUsername()));
        }

        if (userExistsByEmail) {
            throw new EntityAlreadyPresentException(String.format(USER_WITH_EMAIL_ALREADY_EXISTS, user.getEmail()));
        }

        // encoding password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // roles setting
        Set<UserRoleModel> userRoles = configureRoles(roles);
        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    private Set<UserRoleModel> configureRoles(Set<UserRoleModel> roles) {
        Set<UserRoleModel> persistentRoles = userRoleRepository.findAllRolesIn(
                roles.stream()
                        .map(UserRoleModel::getRole)
                        .collect(Collectors.toSet()));

        persistentRoles.addAll(roles);

        return persistentRoles;
    }

    @Transactional
    public void confirmUser(User user) {
        boolean userExistsByUsername = userRepository.findByUsername(user.getUsername()).isPresent();

        if (!userExistsByUsername) {
            throw new EntityNotFoundException(String.format(USER_WITH_USERNAME_NOT_FOUND, user.getUsername()));
        }

        user.makeValid();
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format(USER_WITH_USERNAME_NOT_FOUND, username)));
    }

}
