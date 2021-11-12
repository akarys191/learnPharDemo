package com.pharm.demo.web.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemSumsDTO {
    private Double sumOfPaidSums = 0.0;
    private Double sumOfPricesSums = 0.0;
}
