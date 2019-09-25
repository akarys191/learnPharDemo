package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
//TODO make link to type of payment
public class Sales extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_id_generator")
    @SequenceGenerator(name="sales_id_generator", sequenceName = "sales_id_seq", allocationSize=50)
    private Long salesId;
    @ManyToOne
    private Medicine medicine;
    @ManyToOne
    private Customer customer;
    @ManyToOne
    private Inventory inventory;

    private Double price;
    private Double soldSum;
    private Double quantity;
    private LocalDateTime soldDate;
    @ManyToOne
    private Pharmacist sellingPharmacist;

    @PrePersist
    @PreUpdate
    public void setSoldSum() {
        this.soldSum = this.price * this.quantity;
    }

    public Double getSoldSum() {
        return this.price * this.quantity;
    }
}
