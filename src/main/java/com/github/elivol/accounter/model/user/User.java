package com.github.elivol.accounter.model.user;

import com.github.elivol.accounter.security.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "app_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "fullname")
    private String fullName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<UserRoleModel> roles = new HashSet<>();

    private Boolean isAccountNonExpired = false;
    private Boolean isAccountNonLocked = false;
    private Boolean isCredentialsNonExpired = false;
    private Boolean isEnabled = false;

    public User(Long id, String username, String email, String password, String fullName) {
        this(username, email, password, fullName);
        this.id = id;
    }

    public User(String username, String email, String fullName) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
    }

    public User(String username, String email, String password, String fullName) {
        this(username, email, fullName);
        this.password = password;
    }

    public void makeValid() {
        this.setIsAccountNonExpired(true);
        this.setIsAccountNonLocked(true);
        this.setIsCredentialsNonExpired(true);
        this.setIsEnabled(true);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(UserRoleModel::getRole)
                .map(UserRole::getGrantedAuthorities)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
