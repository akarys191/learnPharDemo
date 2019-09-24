package com.pharm.demo.services;

import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;

public interface InvoiceInventoryFacadeService {
    public void saveInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem invoiceInventoryItem);

    public void deleteInventory(InvoiceInventory invoiceInventory, InvoiceInventoryItem deleteInventory);
}
