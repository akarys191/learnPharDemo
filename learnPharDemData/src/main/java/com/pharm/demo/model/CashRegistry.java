package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CashRegistry extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_registry_id_generator")
    @SequenceGenerator(name = "cash_registry_id_generator", sequenceName = "cash_registry_id_seq", allocationSize = 50)
    private Long cashRegistryId;

    private Long inventoryVersionNumber;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cashRegistry")
    private List<Sales> sales;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cashRegistry")
    private List<CashExpense> cashExpenses;

    @ManyToOne
    @JoinColumn(name = "CASH_INVENTORY_ID")
    CashInventory cashInventory;

    private Double totalCashRegistryMoney = 0.0;
    private Double totalCardRegistryMoney = 0.0;
    private Double totalRegistryMoney = 0.0;

    private LocalDate cashRegistryDate;

    @PrePersist
    @PreUpdate
    public void setTotal() {
        this.totalRegistryMoney = this.totalCardRegistryMoney + this.totalCashRegistryMoney;
    }
}
