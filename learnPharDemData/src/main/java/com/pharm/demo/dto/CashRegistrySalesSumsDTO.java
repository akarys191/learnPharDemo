package com.pharm.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CashRegistrySalesSumsDTO {
    private Long quantityOfSold = 0L;
    private Double sumOfSold = 0.0;
}
