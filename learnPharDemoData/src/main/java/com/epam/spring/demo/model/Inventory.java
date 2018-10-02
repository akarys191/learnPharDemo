package com.epam.spring.demo.model;

import java.time.LocalDateTime;

public class Inventory {
    private Medicine medicine;
    private Supplier supplier;
    private Double price;
    private Double suppliedCost;
    private LocalDateTime suppliedDate;
    private Pharmacist acceptingPharmacist;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSuppliedCost() {
        return suppliedCost;
    }

    public void setSuppliedCost(Double suppliedCost) {
        this.suppliedCost = suppliedCost;
    }

    public LocalDateTime getSuppliedDate() {
        return suppliedDate;
    }

    public void setSuppliedDate(LocalDateTime suppliedDate) {
        this.suppliedDate = suppliedDate;
    }

    public Pharmacist getAcceptPharmacist() {
        return acceptingPharmacist;
    }

    public void setAcceptPharmacist(Pharmacist acceptPharmacist) {
        this.acceptingPharmacist = acceptPharmacist;
    }
}
