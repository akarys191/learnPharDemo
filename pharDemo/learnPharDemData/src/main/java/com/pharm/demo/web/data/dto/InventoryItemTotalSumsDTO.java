package com.pharm.demo.web.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryItemTotalSumsDTO {
    private Double totalSumOfPaidSums = 0.0;
    private Double totalSumOfQuantity = 0.0;
    private Double totalSumOfPricesSums = 0.0;
}
