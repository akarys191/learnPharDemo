package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CashInventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_inventory_id_generator")
    @SequenceGenerator(name = "cash_inventory_id_generator", sequenceName = "cash_inventory_id_seq", allocationSize = 50)
    private Long cashInventoryId;

    private Long inventoryVersionNumber;

    @OneToMany(mappedBy = "cashInventory")
    private List<CashRegistry> cashRegistries;

    @OneToMany(mappedBy = "cashInventory")
    private List<InvoiceInventoryItem> boughtInvoiceInventoryItems;

    private Double totalCashMoney = 0.0;
    private Double totalCardMoney = 0.0;
    private Double totalMoney = 0.0;

    @PrePersist
    @PreUpdate
    public void setTotal() {
        this.totalMoney = this.totalCardMoney + this.totalCashMoney;
    }

    public void addCashRegistry(CashRegistry cashRegistry) {
        if (CollectionUtils.isEmpty(cashRegistries)) {
            cashRegistries = new ArrayList<>();
        }
        cashRegistries.add(cashRegistry);
    }
}
