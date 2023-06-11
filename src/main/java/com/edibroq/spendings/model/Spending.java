package com.edibroq.spendings.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "spendings")
@NoArgsConstructor
@Getter
@Setter
public class Spending {

    @Column
    private BigDecimal amount;

    @Id
    @Column(name = "spender_name")
    private String spender;

    public Spending(BigDecimal amount, String spender) {
        this.amount = amount;
        this.spender = spender;
    }
}
