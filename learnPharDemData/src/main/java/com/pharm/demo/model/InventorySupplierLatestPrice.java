package com.pharm.demo.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InventorySupplierLatestPrice extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_price_id_generator")
    @SequenceGenerator(name = "inventory_price_id_generator", sequenceName = "inventory_price_id_seq", allocationSize = 50)
    private Long inventorySupplierId;

    @ManyToOne
    private Inventory inventory;

    @ManyToOne
    private Supplier supplier;

    private Double latestPrice;

    public InventorySupplierLatestPrice(Inventory inventory, Supplier supplier, Double price) {
    }
}
