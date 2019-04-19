package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class InvoiceInventory extends AbstractEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invoice_inventory_id_generator")
        @SequenceGenerator(name="invoice_inventory_id_generator", sequenceName = "invoice_inventory_id_seq", allocationSize=50)
        private Long invoiceId;
        private Integer numberOfPaidMed;
        private Integer paidSum;
}
