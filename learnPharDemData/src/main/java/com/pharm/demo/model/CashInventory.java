package com.pharm.demo.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CashInventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_inventory_id_generator")
    @SequenceGenerator(name = "cash_inventory_id_generator", sequenceName = "cash_inventory_id_seq", allocationSize = 50)
    private Long cashInventoryId;

    private Long inventoryVersionNumber;

    @OneToMany
    private List<CashRegistry> cashRegistries;

    @OneToMany
    private List<InvoiceInventoryItem> boughtInvoiceInventoryItems;

    private Double totalCashMoney = 0.0;
    private Double totalCardMoney = 0.0;
    private Double totalMoney = 0.0;

    @PrePersist
    @PreUpdate
    public void setTotal() {
        this.totalMoney = this.totalCardMoney + this.totalCashMoney;
    }
}
