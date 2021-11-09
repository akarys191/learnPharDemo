package com.pharm.demo.web.reader.dto;


import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetailSystemRubus {
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
        RetailSystemRubus retailSystemRubus = (RetailSystemRubus) obj;
        return barCode.equals( retailSystemRubus.getBarCode())
                && sellByDate.equals(retailSystemRubus.getSellByDate())
                && name.equals(retailSystemRubus.getName());
    }
}
