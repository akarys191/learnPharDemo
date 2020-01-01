package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class InvestedMoney {
    public InvestedMoney(Double investedSum, String origin, String originMessage, CashInventory cashInventory) {
        this.investedSum = investedSum;
        this.origin = origin;
        this.originMessage = originMessage;
        this.cashInventory = cashInventory;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "invest_money_id_generator")
    @SequenceGenerator(name = "invest_money_id_generator", sequenceName = "invest_money_id_seq", allocationSize = 50)
    private Long investMoneyId;

    private Double investedSum;

    private String origin;
    private String originMessage;

    @ManyToOne
    @JoinColumn(name = "CASH_INVENTORY_ID")
    CashInventory cashInventory;
}
