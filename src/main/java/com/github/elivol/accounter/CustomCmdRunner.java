package com.github.elivol.accounter;

import com.github.elivol.accounter.model.User;
import com.github.elivol.accounter.service.user.UserService;
import com.github.elivol.accounter.security.UserRole;
import com.github.elivol.accounter.security.UserRoleEntity;
import com.github.elivol.accounter.security.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CustomCmdRunner implements CommandLineRunner {

    private final UserService userService;
    private final UserRoleRepository userRoleRepository;


    @Override
    public void run(String... args) throws Exception {
        User user = new User("admin", "admin@accounterproject.com", "admin", "admin");
        user.getRoles().add(new UserRoleEntity(UserRole.ADMIN));
        user.setIsAccountNonExpired(true);
        user.setIsAccountNonLocked(true);
        user.setIsCredentialsNonExpired(true);
        user.setIsEnabled(true);

        userService.create(user);
    }
}
