package com.edibroq.spendings.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "spendings")
@NoArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    private Long id;

    private String name;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "spending", nullable = false)
    private Spending spending;
}
