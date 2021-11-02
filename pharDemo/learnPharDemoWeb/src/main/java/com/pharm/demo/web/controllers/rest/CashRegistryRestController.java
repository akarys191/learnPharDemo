package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InventorySupplierPriceCost;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InventorySupplierLatestService;
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

    private final InventorySupplierLatestService inventorySupplierPriceService;
    private final InventoryService inventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    public CashRegistryRestController(InventoryService inventoryService, InventorySupplierLatestService inventorySupplierPriceService,
                                      InvoiceInventoryContextHolder invoiceInventoryContextHolder) {
        this.inventoryService = inventoryService;
        this.inventorySupplierPriceService = inventorySupplierPriceService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
    }

    @RequestMapping(value = "/findPrice", produces = "application/json")
    public @ResponseBody
    Double findPrice(@Param("supplierId") Long supplierId,
                     @Param("medicineId") Long medicineId) throws PropertyNotFoundException {

        Inventory inventory = findInventory(medicineId);
        Long inventoryId = Optional.ofNullable(inventory).map(Inventory::getInventoryId).orElseThrow(() -> new IllegalStateException(
                String.format("Inventory for medicine %s and for invVersion %s is not found", medicineId, invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber())));

        InventorySupplierPriceCost inventorySupplierLatest = inventorySupplierPriceService.findLatestInventoryByInventoryAndSupplier(inventoryId, supplierId);
        return Optional.ofNullable(inventorySupplierLatest)
                .map(InventorySupplierPriceCost::getPrice)
                .orElseThrow(() -> new PropertyNotFoundException(String.format("No such price for this medicine %s and supplier %s", medicineId, supplierId)));
    }

    private Inventory findInventory(Long medicineId) {
        return inventoryService.findInventoryByVersionNumberAndMedicine(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(), medicineId);
    }
}
