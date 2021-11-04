package com.pharm.demo.web.reader.dto;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RetailSystemRubus {
    private String barCode;
    private LocalDate sellBy;
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
    public boolean equals(Object obj) {

        if(obj==null)
        {
            return true;
        }
        if(this.getClass()!=obj.getClass())
        {
            return false;
        }
        RetailSystemRubus retailSystemRubus = (RetailSystemRubus) obj;
        return barCode.equals( retailSystemRubus.getBarCode())
                && sellBy.equals(retailSystemRubus.getSellBy())
                && name.equals(retailSystemRubus.getName())
                && inStock.equals(retailSystemRubus.getInStock())
                && remain.equals(retailSystemRubus.getRemain())
                && price.equals(retailSystemRubus.getPrice())
                && sum.equals(retailSystemRubus.getSum())
                && producer.equals(retailSystemRubus.getProducer())
                && productGroup.equals(retailSystemRubus.getProductGroup())
                && VAT.equals(retailSystemRubus.getVAT())
                && TVAND.equals(retailSystemRubus.getTVAND())
                && pc.equals(retailSystemRubus.getPc())
                && registrationNumber.equals(retailSystemRubus.getRegistrationNumber());
    }
}
