package com.pharm.demo.web.controllers.rest;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.web.controllers.coverter.ObjectsToResponseConverter;
import com.pharm.demo.web.controllers.coverter.RawParamsToRequestConverter;
import com.pharm.demo.web.model.DataTableRequestTO;
import com.pharm.demo.web.model.DataTableResponseTO;
import com.pharm.demo.web.model.column.ColumnTO;
import com.pharm.demo.web.model.column.SearchTO;
import com.pharm.demo.web.model.raw.RawDataTableRequestDTO;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.el.PropertyNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/rest/inventory")
public class InventoryRestController {

    private final InventoryService inventoryService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;
    private final RawParamsToRequestConverter rawParamsTOConverter;
    private final ObjectsToResponseConverter objectsToResponseConverter;

    public InventoryRestController(InventoryService inventoryService, InvoiceInventoryContextHolder invoiceInventoryContextHolder,
                                   RawParamsToRequestConverter rawParamsTOConverter, ObjectsToResponseConverter objectsToResponseConverter) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
        this.rawParamsTOConverter = rawParamsTOConverter;
        this.objectsToResponseConverter = objectsToResponseConverter;
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

    @RequestMapping(value = "/listDataTable", method = RequestMethod.GET)
    @ResponseBody
    public DataTableResponseTO listDataTable(RawDataTableRequestDTO dataTableRequestDTO) {
        DataTableRequestTO dataTableRequestParam = rawParamsTOConverter.convert(dataTableRequestDTO);
        System.out.println("dataTableRequestParam: " + dataTableRequestParam);
        Long inventoryId = getLongParam(dataTableRequestParam, "inventoryId");
        Long inventoryVersionNumber = getLongParam(dataTableRequestParam, "inventoryVersionNumber");
        String medicineBarcode = getInventoryValue(dataTableRequestParam, "medicine");
        String medicineName = getInventoryValue(dataTableRequestParam, "medicine");
        Page<Inventory> invPage = inventoryService.findInventoriesBySearch(PageRequest.of(dataTableRequestParam.getStart(), dataTableRequestParam.getLength()),
                inventoryId, inventoryVersionNumber, medicineBarcode, medicineName);
        DataTableResponseTO dataTableResponseTO = objectsToResponseConverter.convert(invPage.getContent(), dataTableRequestParam.getDraw(),
                (int) invPage.getTotalElements(), dataTableRequestParam.getLength());
        System.out.println("invList page: " + invPage.getContent());
        System.out.println("invList total: " + invPage.getTotalElements());
        return dataTableResponseTO;
    }

    private Long getLongParam(DataTableRequestTO dataTableRequestParam, String longColumnKey) {
        return Optional.ofNullable(getInventoryValue(dataTableRequestParam, longColumnKey))
                .map(this::getLong)
                .orElse(null);
    }

    private Long getLong(String searchLong) {
        try {
            return Long.parseLong(searchLong);
        } catch (NumberFormatException nf) {
            return null;
        }
    }

    private String getInventoryValue(DataTableRequestTO dataTableRequestParam, String columnKey) {
        return Optional.ofNullable(dataTableRequestParam.getColumns())
                .map(columnMap -> columnMap.get(columnKey))
                .map(ColumnTO::getSearch)
                .map(SearchTO::getValue)
                .orElseThrow(() -> new IllegalStateException("No Columns are present in dataTableRequestParam"));
    }
}
