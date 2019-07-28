package com.pharm.demo.web.util;

public class InvoiceInventoryUtil {

    public static Double calculatePrice(Double suppliedCost, Double markup) {
        return suppliedCost * markup + suppliedCost;
    }

    public static Double calculatePaidSum(Double suppliedCost, Double quantity) {
        return suppliedCost * quantity;
    }

    public static Integer increaseNumberOfPaid(Integer numberOfPaidMed) {
        numberOfPaidMed += 1;
        return numberOfPaidMed;
    }

    public static Double increasePaidSum(Double totalPaidSum, Double paidSum) {
        totalPaidSum = totalPaidSum + paidSum;
        return totalPaidSum;
    }
}
