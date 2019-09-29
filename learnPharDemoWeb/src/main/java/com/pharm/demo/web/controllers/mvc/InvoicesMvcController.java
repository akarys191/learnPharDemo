package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.dto.InventoryItemSumsDTO;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import com.pharm.demo.web.processor.InvoiceInventoryProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/invoices")
@SessionAttributes("invoice")
public class InvoicesMvcController {

    private final InvoiceInventoryService invoiceService;
    private final InvoiceInventoryItemService inventoryService;
    private final InvoiceInventoryProcessor invoiceInventoryProcessor;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String VIEWS_INVOICE_CREATE_OR_UPDATE_FORM = "invoices/createOrUpdateInvoice";
    private static final String VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE = VIEWS_INVOICE_CREATE_OR_UPDATE_FORM + "::editableTable";
    private static int currentInventoryPage = 1;
    private static int currentInventoryPageSize = 50;

    public InvoicesMvcController(InvoiceInventoryService invoiceService, InvoiceInventoryItemService inventoryService,
                                 InvoiceInventoryProcessor invoiceInventoryProcessor) {
        this.invoiceService = invoiceService;
        this.inventoryService = inventoryService;
        this.invoiceInventoryProcessor = invoiceInventoryProcessor;
    }

    @RequestMapping(value = "/listInvoiceInventoryItems", method = RequestMethod.GET)
    public String listInvoiceInventoryItems(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("invoiceId") Long invoiceId) {

        int currentPage = page.orElse(currentInventoryPage);
        int pageSize = size.orElse(currentInventoryPageSize);
        currentInventoryPage = currentPage;
        currentInventoryPageSize = pageSize;
        InvoiceInventory invoiceInventory = invoiceService.findById(invoiceId);
        Page<InvoiceInventoryItem> invoiceInventoryPage = inventoryService.findInvoiceInventoryItemPaginated(PageRequest.of(currentPage - 1, pageSize), invoiceId);
        model.addAttribute("markupPercentage", InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE);
        setInvoiceInventoryPageAttributesToModel(invoiceInventoryPage, model, invoiceInventory);
        return VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE;
    }

    @RequestMapping(value = "/listInvoices", method = RequestMethod.GET)
    public String listInvoices(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);
        Page<InvoiceInventory> invoicePage = invoiceService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("invoicePage", invoicePage);
        int totalPages = invoicePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "invoices/invoices";
    }

    @RequestMapping({"/find"})
    public String findInvoice(Model model) {
        model.addAttribute("invoices", invoiceService.findAll());
        return "invoices/invoices";
    }

    @GetMapping("/{id}")
    public String showInvoice(@PathVariable("id") Long id, Model model) {
        LOGGER.info("Get /id is called! " + id);
        int currentPage = 1;
        int pageSize = currentInventoryPageSize;
        currentInventoryPage = currentPage;
        InvoiceInventory invoiceInventory = invoiceService.findById(id);
        Page<InvoiceInventoryItem> invoiceInventoryPage = inventoryService.findInvoiceInventoryItemPaginated(PageRequest.of(currentPage - 1, pageSize), id);
        model.addAttribute("invoice", invoiceInventory);
        model.addAttribute("markupPercentage", InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE);
        setInvoiceInventoryPageAttributesToModel(invoiceInventoryPage, model, invoiceInventory);
        return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping({"/new"})
    public String getNewInvoice(Model model) {
        InvoiceInventory invoiceInventory = new InvoiceInventory();
        invoiceInventory.setInvoiceInventoryItems(new ArrayList<>());
        Page<InvoiceInventoryItem> invoiceInventoryPage = new PageImpl<>(new ArrayList<>());
        model.addAttribute("invoice", invoiceInventory);
        model.addAttribute("markupPercentage", InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE);
        setInvoiceInventoryPageAttributesToModel(invoiceInventoryPage, model, invoiceInventory);
        return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/inventory")
    public String processCreationForm(@Valid InvoiceInventoryItem inventory,
                                      @ModelAttribute("invoice") InvoiceInventory invoiceInventory,
                                      Model model, BindingResult result) {
        LOGGER.info("Post inventory {1} is called! ", inventory.getInvoiceInventoryItemId());
        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {
            invoiceInventoryProcessor.processSaveInventory(invoiceInventory, inventory);
            Long invoiceId = invoiceInventory.getId();
            Page<InvoiceInventoryItem> invoiceInventoryPage = inventoryService.findInvoiceInventoryItemPaginated(PageRequest.of(currentInventoryPage - 1, currentInventoryPageSize), invoiceId);
            model.addAttribute("markupPercentage", inventory.getMarkupPercentage());
            setInvoiceInventoryPageAttributesToModel(invoiceInventoryPage, model, invoiceInventory);
            return VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE;
        }
    }

    @DeleteMapping("/inventory/{id}")
    public String processDeletionForm(@PathVariable("id") Long invoiceInventoryItemId,
                                      @ModelAttribute("invoice") InvoiceInventory invoiceInventory,
                                      Model model) {
        LOGGER.info("Delete inventory {1} is called! ", invoiceInventoryItemId);
        InvoiceInventoryItem deleteInventory = inventoryService.findById(invoiceInventoryItemId);
        invoiceInventoryProcessor.processDeleteInventory(invoiceInventory, deleteInventory);
        Page<InvoiceInventoryItem> invoiceInventoryPage = inventoryService.findInvoiceInventoryItemPaginated(PageRequest.of(currentInventoryPage - 1, currentInventoryPageSize), invoiceInventory.getId());
        setInvoiceInventoryPageAttributesToModel(invoiceInventoryPage, model, invoiceInventory);
        return VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE;
    }

    private void setInvoiceInventoryPageAttributesToModel(Page invoiceInventoryPage, Model model, InvoiceInventory invoiceInventory) {
        InventoryItemSumsDTO inventoryItemSumsDTO = invoiceService.getTotalPaidPriceSum(invoiceInventory.getId());
        model.addAttribute("invoiceInventoryPage", invoiceInventoryPage);
        model.addAttribute("totalPaidSum", inventoryItemSumsDTO.getSumOfPaidSums());
        model.addAttribute("totalPriceSum", inventoryItemSumsDTO.getSumOfPricesSums());
        model.addAttribute("totalPaidNum", invoiceInventory.getTotalPaidNum());
        model.addAttribute("totalPages", invoiceInventoryPage.getTotalPages());
    }
}
