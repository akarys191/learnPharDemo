package com.pharm.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
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
    @JoinColumn(name = "INVOICE_ID")
    private InvoiceInventory invoice;

    @ManyToOne
    private Supplier supplier;
    private Integer quantity;
    private Double price;
    private Double markup;
    private Double suppliedCost;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime suppliedDate;
    @ManyToOne
    private Pharmacist acceptingPharmacist;

    public static Double DEFAULT_MARKUP = 0.2;

    public Inventory(Medicine medicine, Supplier supplier, Integer quantity,
                     Double markup, Double price, Double suppliedCost, LocalDateTime suppliedDate, Pharmacist acceptingPharmacist) {
        this.medicine = medicine;
        this.supplier = supplier;
        this.quantity = quantity;
        this.markup = markup;
        this.price = price;
        this.suppliedCost = suppliedCost;
        this.suppliedDate = suppliedDate;
        this.acceptingPharmacist = acceptingPharmacist;
    }
}
