package com.github.elivol.accounter.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.elivol.accounter.model.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @Column(nullable = false)
    private Boolean isIncoming = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return Objects.equals(id, operation.id) &&
                Objects.equals(createdAt, operation.createdAt) &&
                Objects.equals(amount, operation.amount) &&
                Objects.equals(isIncoming, operation.isIncoming) &&
                Objects.equals(account, operation.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdAt, amount, isIncoming, account);
    }
}
