package com.github.elivol.accounter;

import com.github.elivol.accounter.dto.model.UserRegistrationRequest;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.repository.UserRepository;
import com.github.elivol.accounter.service.user.UserService;
import com.github.elivol.accounter.security.UserRole;
import com.github.elivol.accounter.model.user.UserRoleModel;
import com.github.elivol.accounter.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class CustomCmdRunner implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public void run(String... args) throws Exception {

        UserRegistrationRequest request = new UserRegistrationRequest("admin", "admin@accounterproject.com", "admin", "admin");

        Set<UserRoleModel> userRoleModels = Set.of(
                new UserRoleModel(UserRole.USER),
                new UserRoleModel(UserRole.ADMIN));

        User user = userService.createWithRoles(request, userRoleModels);
        userService.confirmUser(user);
    }
}
