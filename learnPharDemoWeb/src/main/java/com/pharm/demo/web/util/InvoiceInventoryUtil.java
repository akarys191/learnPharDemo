package com.pharm.demo.web.util;

public class InvoiceInventoryUtil {

    public static Double calculatePrice(Double suppliedCost, Double markup) {
        return suppliedCost * markup + suppliedCost;
    }
}
