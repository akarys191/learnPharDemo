package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.dto.MedicineDTO;
import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.Medicine;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.MedicineService;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/rest/medicines")
public class MedicinesRestController {

    private final MedicineService medicineService;
    private final InventoryService inventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    public MedicinesRestController(MedicineService medicineService, InventoryService inventoryService,
                                   InvoiceInventoryContextHolder invoiceInventoryContextHolder) {
        this.medicineService = medicineService;
        this.inventoryService = inventoryService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
    }

    @RequestMapping({"/all"})
    public Set<Medicine> listAllMedicines() {
        return medicineService.findAll();
    }

    @RequestMapping({"/findByName"})
    public @ResponseBody
    List<Medicine> findMedicineByName(@Param("term") String term) {
        return medicineService.findByNameTerm(term);
    }


    @RequestMapping(value = "/findByBarcode", produces = " application/json")
    public @ResponseBody
    MedicineDTO findByBarcode(@Param("term") String term, @Param("isSales") Boolean isSales) {
        Medicine medicine = medicineService.findByBarcode(term);
        MedicineDTO medicineDTO = new MedicineDTO();
        BeanUtils.copyProperties(medicine, medicineDTO);
        if (isSales) {
            Inventory inventory = inventoryService.findInventoryByVersionNumberAndMedicine(invoiceInventoryContextHolder.getActiveInvoiceInventoryVersionNumber(),
                    medicine.getId());
            if (inventory != null) {
                medicineDTO.setPrice(inventory.getPrice());
            }
        }
        return medicineDTO;
    }
}
