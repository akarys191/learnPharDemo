package com.epam.spring.demo.model;

import java.time.LocalDateTime;

public class Sales  {
    private Medicine medicine;
    private Customer customer;
    private Double price;
    private Double soldSum;
    private Integer quantity;
    private LocalDateTime soldDate;
    private Pharmacist sellingPharmacist;

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getSoldSum() {
        return soldSum;
    }

    public void setSoldSum(Double soldSum) {
        this.soldSum = soldSum;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(LocalDateTime suppliedDate) {
        this.soldDate = suppliedDate;
    }

    public Pharmacist getSellingPharmacist() {
        return sellingPharmacist;
    }

    public void setSellingPharmacist(Pharmacist sellingPharmacist) {
        this.sellingPharmacist = sellingPharmacist;
    }
}
