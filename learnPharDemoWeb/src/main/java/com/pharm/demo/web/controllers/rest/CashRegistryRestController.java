package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InventorySupplierLatestPrice;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InventorySupplierPriceService;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.el.PropertyNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/rest/cashRegistry")
public class CashRegistryRestController {

    private final InventorySupplierPriceService inventorySupplierPriceService;
    private final InventoryService inventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    public CashRegistryRestController(InventoryService inventoryService, InventorySupplierPriceService inventorySupplierPriceService,
                                      InvoiceInventoryContextHolder invoiceInventoryContextHolder) {
        this.inventoryService = inventoryService;
        this.inventorySupplierPriceService = inventorySupplierPriceService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
    }

    @RequestMapping(value = "/findPrice", produces = " application/json")
    public @ResponseBody
    Double findPrice(@Param("supplierId") Long supplierId,
                     @Param("medicineId") Long medicineId) throws PropertyNotFoundException {

        Inventory inventory = findInventory(medicineId);
        Long inventoryId = inventory.getInventoryId();

        InventorySupplierLatestPrice inventorySupplierLatestPrice = inventorySupplierPriceService.findInventoryByInventoryAndSupplier(inventoryId, supplierId);
        return Optional.ofNullable(inventorySupplierLatestPrice)
                .map(InventorySupplierLatestPrice::getLatestPrice)
                .orElseThrow(() -> new PropertyNotFoundException(String.format("No such price for this medicine %s and supplier %s", medicineId, supplierId)));
    }

    private Inventory findInventory(Long medicineId) {
        return inventoryService.findInventoryByVersionNumberAndMedicine(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), medicineId);
    }
}
