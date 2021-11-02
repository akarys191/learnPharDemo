package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import javassist.NotFoundException;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.el.PropertyNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/rest/inventory")
public class InventoryRestController {

    private final InventoryService inventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    public InventoryRestController(InventoryService inventoryService, InvoiceInventoryContextHolder invoiceInventoryContextHolder) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
    }

    @RequestMapping(value = {"/quantity"}, produces = " application/json")
    @ResponseBody
    public Double inventoryMedicineQuantity(@Param("medicineId") Long medicineId) throws NotFoundException {
        Long inventoryVersionNumber = invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber();
        Inventory inventory = inventoryService.findInventoryByVersionNumberAndMedicine(inventoryVersionNumber,
                medicineId);
        return Optional.ofNullable(inventory)
                .map(Inventory::getTotalActiveQuantity)
                .orElseThrow(() -> new PropertyNotFoundException(String.format(
                        "No inventory found for medicine %s and inventory version %s",
                        medicineId, inventoryVersionNumber
                )));
    }
}
