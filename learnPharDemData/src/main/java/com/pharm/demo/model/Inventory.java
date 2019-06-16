package com.pharm.demo.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Inventory extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_generator")
    @SequenceGenerator(name="inventory_id_generator", sequenceName = "inventory_id_seq", allocationSize=50)
    private Long inventoryId;
    @ManyToOne
    private Medicine medicine;
    @ManyToOne
    private InvoiceInventory invoice;
    @ManyToOne
    private Supplier supplier;
    private Integer quantity;
    private Double price;
    private Double suppliedCost;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate suppliedDate;
    @ManyToOne
    private Pharmacist acceptingPharmacist;
}
