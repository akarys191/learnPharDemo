package com.pharm.demo.web.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@NoArgsConstructor
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
    @JoinColumn(name = "SUPPLIER_ID")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "INVENTORY_ID")
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "CASH_REGISTRY_ID")
    private CashRegistry cashRegistry;

    @Enumerated(EnumType.STRING)
    CashType cashType;

    @NonNull
    private Double price;
    private Double soldCost;
    private Double soldSum;
    @NonNull
    private Double quantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime soldDate;

    @OneToOne(mappedBy = "sales")
    private CardPayment cardPayment;

    @ManyToOne
    private Pharmacist sellingPharmacist;

    @PrePersist
    @PreUpdate
    public void setSoldSum() {
        this.soldSum = this.price * this.quantity;
        this.soldCost = this.soldCost * this.quantity;
    }

    public Double getSoldSum() {
        return this.price * this.quantity;
    }

    public Double getCardPayment() {
        return Optional.ofNullable(this.cardPayment).map(CardPayment::getPaymentAmount).orElse(0.0);
    }
}
