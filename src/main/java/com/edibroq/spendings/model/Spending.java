package com.edibroq.spendings.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "spendings")
@NoArgsConstructor
@Getter
@Setter
public class Spending {

    @Column
    private BigDecimal amount;

    @Id
    private String spender;

    @OneToMany
    private Set<Item> items;

    public Spending(BigDecimal amount, String spender) {
        this.amount = amount;
        this.spender = spender;
    }
}
