package com.pharm.demo.web.processor;

import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;

public interface InvoiceInventoryProcessor {
    void processSaveInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem);

    void processDeleteInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem deleteInventory);
}
