package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
    private Integer quantity;
    private LocalDateTime soldDate;
    @ManyToOne
    private Pharmacist sellingPharmacist;
}
