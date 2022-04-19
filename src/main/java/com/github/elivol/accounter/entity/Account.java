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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

}
