package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceInventory extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_inventory_id_generator")
    @SequenceGenerator(name = "invoice_inventory_id_generator", sequenceName = "invoice_inventory_id_seq", allocationSize = 50)
    private Long id;
    @Transient
    private Double paidSum = 0.0;

    @ManyToOne
    private Supplier supplier;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "invoice",
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Inventory> inventories;


}
