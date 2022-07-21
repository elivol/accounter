package com.github.elivol.accounter.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "app_currency")
public class AppCurrency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "currency_code", unique = true)
    private String currencyCode;

    public AppCurrency(String currencyCode) {
        this.currencyCode = currencyCode.toUpperCase();
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode.toUpperCase();
    }

    @Override
    public String toString() {
        return currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppCurrency that = (AppCurrency) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
