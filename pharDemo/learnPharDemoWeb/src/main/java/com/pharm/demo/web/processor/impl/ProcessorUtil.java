package com.pharm.demo.web.processor.impl;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ProcessorUtil {

    boolean isOldValueChanged(Double existingValue, Double newValue) {
        Objects.requireNonNull(existingValue);
        return !existingValue.equals(newValue);
    }

    Double reAddToTotal(Double existingValue, Double newValue, Double totalExistingValue) {
        Objects.requireNonNull(totalExistingValue);
        Objects.requireNonNull(existingValue);
        return totalExistingValue - existingValue + newValue;
    }

    Double reDeductToTotal(Double existingValue, Double newValue, Double totalExistingValue) {
        Objects.requireNonNull(totalExistingValue);
        Objects.requireNonNull(existingValue);
        return totalExistingValue + existingValue - newValue;
    }

    Double addNewValue(Double existingValue, Double newValue) {
        return existingValue + newValue;
    }

    Double deductNewValue(Double existingValue, Double newValue) {
        return existingValue - newValue;
    }
}