package com.pharm.demo.order.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="ORDER")
@Setter
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;
    @Column(name = "name_of_medicine", nullable = false)
    private String nameOfMedicine;
    @Column(name = "amount_of_medicine", nullable = false)
    private Integer amountOfMedicine;
    @Column(name = "expected_date", nullable = false)
    private LocalDate expectedDate;
    @Column(name = "sale_date", nullable = false)
    private LocalDate saleDate;

    public Order(User user, String nameOfMedicine, Integer amountOfMedicine, LocalDate expectedDate, LocalDate saleDate) {
        this.user = user;
        this.nameOfMedicine = nameOfMedicine;
        this.amountOfMedicine = amountOfMedicine;
        this.expectedDate = expectedDate;
        this.saleDate = saleDate;
    }
}
