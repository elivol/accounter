package com.github.elivol.accounter.model.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elivol.accounter.admin.currency.AppCurrency;
import com.github.elivol.accounter.model.exchangerate.ExchangeRate;
import com.github.elivol.accounter.model.operation.Operation;
import com.github.elivol.accounter.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    @JsonIgnore
    private List<Operation> operations = new ArrayList<>();

    /*
    * String 3 letters representation for currency
    * Received from user's request, then transformed to AppCurrency object
    * */
    @JsonProperty("currency")
    @NotNull
    @Transient
    private String currencyString;

    /*
    * Currency DTO
    * */
    @OneToOne
    @JoinColumn(name = "currency_id")
    @JsonIgnore
    private AppCurrency currency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Transient
    @JsonIgnore
    private ExchangeRate exchangeRate;

    /*
    * Gets the result of the request to exchange rate service
    * */
    @JsonProperty("exchange_rate")
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }

}
