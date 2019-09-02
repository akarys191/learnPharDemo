package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/rest/invoices")
public class InvoicesRestController {

    private final InvoiceInventoryService invoiceService;
    private final InventoryService inventoryService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String VIEWS_INVOICE_CREATE_OR_UPDATE_FORM = "/invoices/createOrUpdateInvoice";
    private static final String VIEWS_INVOICE_INVENTORY_CREATE_OR_UPDATE_FORM = "invoices/createOrUpdateInvoiceInventory";

    public InvoicesRestController(InvoiceInventoryService invoiceService, InventoryService inventoryService) {
        this.invoiceService = invoiceService;
        this.inventoryService = inventoryService;
    }
}
