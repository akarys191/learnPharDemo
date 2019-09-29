package com.pharm.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_generator")
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "inventory_id_seq", allocationSize = 50)
    private Long inventoryId;

    private Long inventoryVersionNumber;

    @NotNull
    @ManyToOne
    private Medicine medicine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory")
    private List<InvoiceInventoryItem> invoiceInventoryItems;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory")
    private List<Sales> sales;

    private Double totalBoughtQuantity = 0.0;

    private Double totalSoldQuantity = 0.0;

    private Double totalBoughtCost = 0.0;
    private Double totalSoldCost = 0.0;

    private Double totalBoughtPriceSum = 0.0;
    private Double totalSoldPriceSum = 0.0;


    private Double totalActiveQuantity = 0.0;
    private Double totalActiveCost = 0.0;
    private Double totalActivePriceSum = 0.0;

    @PrePersist
    @PreUpdate
    public void setTotalActive() {
        this.totalActiveQuantity = this.totalBoughtQuantity - this.totalSoldQuantity;
        this.totalActiveCost = this.totalBoughtCost - this.totalSoldCost;
        this.totalActivePriceSum = this.totalBoughtPriceSum - this.totalSoldPriceSum;
    }

    public Integer getInvoiceInventoryItemsSize() {
        return Optional.ofNullable(invoiceInventoryItems).map(List::size).orElse(0);
    }

    public Integer getSalesSize() {
        return Optional.ofNullable(sales).map(List::size).orElse(0);
    }
}
