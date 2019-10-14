package com.pharm.demo.web.processor.impl;

import com.pharm.demo.services.CashRegistryService;
import com.pharm.demo.web.processor.CashRegistryProcessor;
import com.pharm.demo.web.processor.InvoiceInventoryProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CashRegistryProcessorImpl implements CashRegistryProcessor {


    private final CashRegistryService cashRegistryService;
    private final InvoiceInventoryProcessor invoiceInventoryProcessor;

    private Long currentInventoryVersionNumber;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public CashRegistryProcessorImpl(CashRegistryService cashRegistryService, InvoiceInventoryProcessor invoiceInventoryProcessor) {
        this.cashRegistryService = cashRegistryService;
        this.invoiceInventoryProcessor = invoiceInventoryProcessor;
    }
}
