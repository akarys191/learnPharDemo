package com.pharm.demo.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inventory_id_generator")
    @SequenceGenerator(name = "inventory_id_generator", sequenceName = "inventory_id_seq", allocationSize = 50)
    private Long inventoryId;

    @NotNull
    @ManyToOne
    private Medicine medicine;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory",
            cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<InvoiceInventoryItem> invoiceInventoryItems;

    @NotNull
    private Double totalActiveQuantity;

    @NotNull
    private Double totalBoughtQuantity;

    @NotNull
    private Double totalSoldQuantity;

    @NotNull
    private Double totalBoughtCost;

    @NotNull
    private Double totalActiveCost;

    @NotNull
    private Double totalSoldSum;
}
