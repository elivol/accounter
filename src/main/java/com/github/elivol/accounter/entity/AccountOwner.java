package com.github.elivol.accounter.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account_owner")
public class AccountOwner {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_owner_sequence"
    )
    @SequenceGenerator(
            name = "account_owner_sequence",
            sequenceName = "account_owner_sequence",
            allocationSize = 1
    )
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String contacts;

    @Override
    public String toString() {
        return "AccountOwner{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", contacts='" + contacts + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AccountOwner that = (AccountOwner) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
