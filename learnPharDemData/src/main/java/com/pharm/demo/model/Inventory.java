package com.pharm.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

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
    @SequenceGenerator(name="inventory_id_generator", sequenceName = "inventory_id_seq", allocationSize=50)
    private Long inventoryId;

    @NotNull
    @ManyToOne
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "INVOICE_ID")
    private InvoiceInventory invoice;

    @NotNull
    @ManyToOne
    private Supplier supplier;

    @NotNull
    private Double quantity;
    private Double price;
    private Double markup;

    @NotNull
    private Double suppliedCost;
    private Double paidSum;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime suppliedDate;

    @NotNull
    @ManyToOne
    private Pharmacist acceptingPharmacist;

    public static Double DEFAULT_MARKUP = 0.2;

    public Inventory(Medicine medicine, Supplier supplier, Double quantity,
                     Double markup, Double price, Double suppliedCost, LocalDateTime suppliedDate, Pharmacist acceptingPharmacist) {
        this.medicine = medicine;

        this.supplier = supplier;
        this.quantity = quantity;
        this.markup = markup;
        this.price = price;
        this.suppliedCost = suppliedCost;
        this.suppliedDate = suppliedDate;
        this.acceptingPharmacist = acceptingPharmacist;
        this.paidSum = this.quantity * this.suppliedCost;
    }

    @PrePersist
    @PreUpdate
    public void setPaidSum() {
        this.paidSum = this.quantity * this.suppliedCost;
    }

    public Double getPaidSum() {
        return this.paidSum;
    }
}
