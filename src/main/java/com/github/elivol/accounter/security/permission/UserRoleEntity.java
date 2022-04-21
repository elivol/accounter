package com.github.elivol.accounter.security.permission;

import com.github.elivol.accounter.security.permission.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "app_role")
public class UserRoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name")
    private UserRole role;

    public UserRoleEntity(UserRole role) {
        this.role = role;
    }

}
