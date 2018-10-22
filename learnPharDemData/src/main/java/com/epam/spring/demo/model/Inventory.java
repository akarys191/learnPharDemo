/*
package com.epam.spring.demo.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventories")
public class Inventory  extends BaseEntity{
    private Medicine medicine;
    private Supplier supplier;
    private Double price;
    private Double suppliedCost;
    private LocalDateTime suppliedDate;
    private Pharmacist acceptingPharmacist;

    @
    public Medicine getMedicine() {
        return medicine;
    }
    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    public Supplier getSupplier() {
        return supplier;
    }
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Column(name = "price")
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    @Column(name = "suppliedCost")
    public Double getSuppliedCost() {
        return suppliedCost;
    }
    public void setSuppliedCost(Double suppliedCost) {
        this.suppliedCost = suppliedCost;
    }

    @Column(name = "suppliedDate")
    public LocalDateTime getSuppliedDate() {
        return suppliedDate;
    }
    public void setSuppliedDate(LocalDateTime suppliedDate) {
        this.suppliedDate = suppliedDate;
    }

    @ManyToOne
    @JoinColumn(name = "pharmacist_id")
    public Pharmacist getAcceptPharmacist() {
        return acceptingPharmacist;
    }
    public void setAcceptPharmacist(Pharmacist acceptPharmacist) {
        this.acceptingPharmacist = acceptPharmacist;
    }
}
*/
