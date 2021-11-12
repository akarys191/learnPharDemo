package com.pharm.demo.web.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesSumsDTO {

    public SalesSumsDTO(Long quantityOfSold, Double sumOfSold) {
        this.quantityOfSold = quantityOfSold;
        this.sumOfSold = sumOfSold;
    }

    private Long version;
    private Long quantityOfSold = 0L;
    private Double sumOfSold = 0.0;
}
