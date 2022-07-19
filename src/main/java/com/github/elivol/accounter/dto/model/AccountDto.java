package com.github.elivol.accounter.dto.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elivol.accounter.dto.model.exchangerate.ExchangeRate;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@ToString
public class AccountDto {

    @JsonIgnore
    private Long id;

    @NotNull private BigDecimal balance;
    @NotNull private String currency;

    @JsonIgnore
    private ExchangeRate exchangeRate;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("exchange_rate")
    public ExchangeRate getExchangeRate() {
        return exchangeRate;
    }
}
