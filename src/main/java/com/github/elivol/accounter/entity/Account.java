package com.github.elivol.accounter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;

    @Column(
            nullable = false,
            unique = true
    )
    private String number;

    @Column(
            nullable = false
    )
    private BigDecimal balance;

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "owner_id")
    private AccountOwner owner;

    public Account(String number, BigDecimal balance, AccountOwner owner) {
        this.number = number;
        this.balance = balance;
        this.owner = owner;
    }
}
