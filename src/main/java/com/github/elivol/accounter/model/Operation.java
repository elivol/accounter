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

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @Column(nullable = false)
    @JsonProperty("is_incoming")
    private Boolean isIncoming = false;

    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @JsonGetter("account_id")
    public Long getAccountId() {
        return account.getId();
    }

}
