package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Getter
@Setter
public class Sales extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sales_id_generator")
    @SequenceGenerator(name = "sales_id_generator", sequenceName = "sales_id_seq", allocationSize = 50)
    private Long salesId;

    @ManyToOne
    @JoinColumn(name = "MEDICINE_ID")
    private Medicine medicine;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "INVENTORY_ID")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "CASH_REGISTRY_ID")
    private CashRegistry cashRegistry;

    @Enumerated(EnumType.STRING)
    CashType cashType;

    private Double price;
    private Double soldSum;
    private Double quantity;
    private LocalDateTime soldDate;

    @OneToOne(mappedBy = "sales")
    private CardPayment cardPayment;

    @ManyToOne
    private Pharmacist sellingPharmacist;

    @PrePersist
    @PreUpdate
    public void setSoldSum() {
        this.soldSum = this.price * this.quantity;
    }

    public Double getSoldSum() {
        return this.price * this.quantity;
    }

    public Double getCardPayment() {
        return Optional.ofNullable(this.cardPayment).map(CardPayment::getPaymentAmount).orElse(0.0);
    }
}
