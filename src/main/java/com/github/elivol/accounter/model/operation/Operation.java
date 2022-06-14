package com.github.elivol.accounter.model.operation;

import com.github.elivol.accounter.model.account.Account;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
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

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id")
    private Account account;

}
