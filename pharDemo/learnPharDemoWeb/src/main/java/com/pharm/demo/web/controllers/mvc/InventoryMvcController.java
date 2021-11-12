package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.web.data.model.Inventory;
import com.pharm.demo.web.data.model.InvoiceInventoryItem;
import com.pharm.demo.web.data.services.InventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/inventory")
public class InventoryMvcController {
    private final InventoryService inventoryService;


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM = "inventory/createOrUpdateInventory";

    public InventoryMvcController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @RequestMapping({"/inventory/", "/inventory", "inventory", "inventory.html", "inventory/"})
    public String listInventory(Model model) {
        model.addAttribute("inventory", inventoryService.findAll());

        return "inventory/inventory";
    }

    @RequestMapping(value = "/listInventory", method = RequestMethod.GET)
    public String listInventory(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);

        Page<Inventory> inventoryPage = inventoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("inventoryPage", inventoryPage);

        int totalPages = inventoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "inventory/inventory";
    }

    @GetMapping("/listInvoiceInventoryItems/{inventoryId}")
    public String listInvoiceInventoryItems(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @PathVariable("inventoryId") Long inventoryId) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);

        Page<InvoiceInventoryItem> inventoryInvoiceInventoryPage = inventoryService.findInvoiceInventoryItemsByInventoryIdPaginated(PageRequest.of(currentPage - 1, pageSize), inventoryId);
        Inventory inventory = inventoryService.findById(inventoryId);
        model.addAttribute("inventoryInvoiceInventoryPage", inventoryInvoiceInventoryPage);
        model.addAttribute("inventoryVersion", inventory.getInventoryVersionNumber());
        model.addAttribute("medicineName", inventory.getMedicine().getName());
        model.addAttribute("inventoryId", inventoryId);

        int totalPages = inventoryInvoiceInventoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "inventory/inventoryInvoiceInventoryItems";
    }

    @GetMapping("/{inventoryId}")
    public String viewInventory(
            Model model,
            @PathVariable("inventoryId") Long inventoryId) {
        LOGGER.info("Get /invoiceInventoryItemId is called! " + inventoryId);
        Inventory inventory = inventoryService.findById(inventoryId);
        model.addAttribute("inventory", inventory);
        model.addAttribute("inventoryVersion", inventory.getInventoryVersionNumber());
        model.addAttribute("medicineName", inventory.getMedicine().getName());
        model.addAttribute("inventoryId", inventoryId);
        return "inventory/inventoryDetails";
    }

    @RequestMapping({"/find"})
    public String findInventory(Model model) {
        model.addAttribute("inventory", inventoryService.findAll());

        return "inventory/inventory";
    }

    @GetMapping({"/new"})
    public String getNewInventory(Model model) {
        model.addAttribute("invoiceInventoryItems", inventoryService.findAll());
        model.addAttribute("inventory", new InvoiceInventoryItem());
        return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping("/{invoiceInventoryItemId}/edit")
    public String initUpdateInventoryForm(@PathVariable("invoiceInventoryItemId") Long invoiceInventoryItemId, Model model) {
        model.addAttribute("inventory", inventoryService.findById(invoiceInventoryItemId));
        return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
    }
}
