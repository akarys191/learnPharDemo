package com.pharm.demo.web.processor;

import com.pharm.demo.web.data.model.InvoiceInventory;
import com.pharm.demo.web.data.model.InvoiceInventoryItem;

public interface InvoiceInventoryProcessor {
    void processSaveInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem);

    void processDeleteInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem deleteInventory);
}
