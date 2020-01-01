package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Inventory extends AbstractEntity {

    public Inventory(Long inventoryVersionNumber, @NotNull Medicine medicine) {
        this.inventoryVersionNumber = inventoryVersionNumber;
        this.medicine = medicine;
    }

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

    public List<InvoiceInventoryItem> getInvoiceInventoryItems() {
        if (Objects.isNull(invoiceInventoryItems)) {
            this.invoiceInventoryItems = new ArrayList<>();
        }
        return this.invoiceInventoryItems;
    }

    public List<Sales> getSales() {
        if (Objects.isNull(sales)) {
            this.sales = new ArrayList<>();
        }
        return sales;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory")
    private List<Sales> sales;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory")
    List<InventorySupplierPriceCost> inventorySupplierLatestPriceCosts;

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
