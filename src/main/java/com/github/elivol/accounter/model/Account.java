package com.github.elivol.accounter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elivol.accounter.dto.model.exchangerate.ExchangeRate;
import com.github.elivol.accounter.model.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    private BigDecimal balance;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Operation> operations = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "currency_id")
    private AppCurrency currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    private ExchangeRate exchangeRate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(id, account.id) &&
                Objects.equals(balance, account.balance) &&
                Objects.equals(currency, account.currency) &&
                Objects.equals(user, account.user) &&
                Objects.equals(exchangeRate, account.exchangeRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, currency, user, exchangeRate);
    }
}
