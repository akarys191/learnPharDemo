package com.pharm.demo.stock.data.model;


import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetailSystemStock {
    @Id
    private BigInteger _id;
    private String barCode;
    private LocalDate sellByDate;
    private String name;
    private Double inStock;
    private Remain remain;
    private Double price;
    private Double sum;
    private String producer;
    private String productGroup;
    private Boolean VAT;
    private String TVAND;
    private Double pc;
    private String registrationNumber;

    @Override
    public int hashCode() {
        return Objects.hash(barCode,sellByDate,name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) { return true; }
        if (this.getClass() != obj.getClass()) { return false; }
        RetailSystemStock retailSystemStock = (RetailSystemStock) obj;
        return barCode.equals( retailSystemStock.getBarCode())
                && sellByDate.equals(retailSystemStock.getSellByDate())
                && name.equals(retailSystemStock.getName());
    }
}
