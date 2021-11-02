package com.pharm.demo.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CashExpense extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cash_expense_id_generator")
    @SequenceGenerator(name = "cash_expense_id_generator", sequenceName = "cash_expense_id_seq", allocationSize = 50)
    private Long cashExpenseId;

    private String fullName;
    private Double expenseAmount = 0.0;
    private LocalDateTime expenseDate = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "CASH_INVENTORY_ID")
    CashInventory cashInventory;

    @ManyToOne
    @JoinColumn(name = "CASH_REGISTRY_ID")
    CashRegistry cashRegistry;
}
