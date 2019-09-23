package com.pharm.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
public class Inventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_generator")
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "inventory_id_seq", allocationSize = 50)
    private Long inventoryId;

    @NotNull
    @ManyToOne
    private Medicine medicine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory",
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    private InvoiceInventoryItem invoiceInventoryItem;

    @NotNull
    private Double totalQuantity;

    @NotNull
    private Double totalCost;

    @NotNull
    private Double totalPaidSum;

    @NotNull
    @ManyToOne
    private Pharmacist acceptingPharmacist;

    public static Double DEFAULT_MARKUP_PERCENTAGE = 25.0;

    public Inventory(Medicine medicine, Supplier supplier, Double quantity,
                     Double markup, Double price, Double suppliedCost, LocalDateTime suppliedDate, Pharmacist acceptingPharmacist) {
        this.medicine = medicine;
        this.totalQuantity = quantity;
        this.totalCost = suppliedCost;
        this.acceptingPharmacist = acceptingPharmacist;
        this.totalPaidSum = this.totalQuantity * this.totalCost;
    }

    @PrePersist
    @PreUpdate
    public void setTotalPaidSum() {
        this.totalPaidSum = this.totalQuantity * this.totalCost;
    }

    public Double getTotalPaidSum() {
        return this.totalPaidSum;
    }
}
