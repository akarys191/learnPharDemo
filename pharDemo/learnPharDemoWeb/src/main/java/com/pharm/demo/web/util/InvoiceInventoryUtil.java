package com.pharm.demo.web.util;

public class InvoiceInventoryUtil {
    public static Double increasePaidSum(Double inventoryPaidSum, Double invoicePaidSum) {
        return invoicePaidSum + inventoryPaidSum;
    }

    public static Double decreasePaidSum(Double inventoryPaidSum, Double invoicePaidSum) {
        return invoicePaidSum - inventoryPaidSum;
    }

    public static Double calculatePrice(Double suppliedCost, Double markup) {
        return suppliedCost * markup + suppliedCost;
    }
}
