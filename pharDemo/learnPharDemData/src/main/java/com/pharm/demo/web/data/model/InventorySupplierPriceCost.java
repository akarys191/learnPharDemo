package com.pharm.demo.web.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InventorySupplierPriceCost extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_latest_id_generator")
    @SequenceGenerator(name = "inventory_latest_id_generator", sequenceName = "inventory_latest_id_seq", allocationSize = 50)
    private Long inventorySupplierLatestId;

    @ManyToOne
    private Inventory inventory;

    @ManyToOne
    private InvoiceInventoryItem latestInvoiceInventoryItem;

    @ManyToOne
    private Sales latestSales;


    @ManyToOne
    private Supplier supplier;

    private Double price;
    private Double cost;

    private LocalDateTime creationDateTime;

    public static InventorySupplierPriceCostBuilder builder() {
        return new InventorySupplierPriceCostBuilder();
    }

    public static class InventorySupplierPriceCostBuilder {
        private Inventory inventory;
        private InvoiceInventoryItem latestInvoiceInventoryItem;
        private Sales latestSales;
        private Supplier supplier;
        private Double price;
        private Double cost;

        public InventorySupplierPriceCostBuilder withInventory(Inventory inventory) {
            this.inventory = inventory;
            return this;
        }

        public InventorySupplierPriceCostBuilder withInvoiceInventoryItemSupplierPriceCost(InvoiceInventoryItem invoiceInventoryItem) {
            Objects.requireNonNull(invoiceInventoryItem);
            Objects.requireNonNull(invoiceInventoryItem.getSupplier());
            Objects.requireNonNull(invoiceInventoryItem.getPrice());
            Objects.requireNonNull(invoiceInventoryItem.getSuppliedCost());
            this.latestInvoiceInventoryItem = invoiceInventoryItem;
            this.price = invoiceInventoryItem.getPrice();
            this.supplier = invoiceInventoryItem.getSupplier();
            this.cost = invoiceInventoryItem.getSuppliedCost();
            return this;
        }

        public InventorySupplierPriceCostBuilder withSalesSupplierPriceCost(Sales cashRegistrySales) {
            Objects.requireNonNull(cashRegistrySales);
            Objects.requireNonNull(cashRegistrySales.getPrice());
            Objects.requireNonNull(cashRegistrySales.getSupplier());
            Objects.requireNonNull(cashRegistrySales.getSoldCost());
            this.latestSales = cashRegistrySales;
            this.price = cashRegistrySales.getPrice();
            this.supplier = cashRegistrySales.getSupplier();
            this.cost = cashRegistrySales.getSoldCost();
            return this;
        }

        public InventorySupplierPriceCost build() {
            return new InventorySupplierPriceCost(this.inventory, this.latestInvoiceInventoryItem, this.latestSales, this.supplier, this.price, this.cost);
        }
    }

    public InventorySupplierPriceCost(Inventory inventory, InvoiceInventoryItem latestInvoiceInventoryItem, Sales latestSales, Supplier supplier, Double price, Double cost) {
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(supplier);
        Objects.requireNonNull(price);
        Objects.requireNonNull(cost);

        this.inventory = inventory;
        this.latestInvoiceInventoryItem = latestInvoiceInventoryItem;
        this.latestSales = latestSales;
        this.supplier = supplier;
        this.price = price;
        this.cost = cost;
        this.creationDateTime = LocalDateTime.now();
    }
}
