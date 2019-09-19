package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/invoices")
@SessionAttributes("invoice")
public class InvoicesMvcController {

    private final InvoiceInventoryService invoiceService;
    private final InventoryService inventoryService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String VIEWS_INVOICE_CREATE_OR_UPDATE_FORM = "invoices/createOrUpdateInvoice";
    private static final String VIEWS_INVOICE_INVENTORY_CREATE_OR_UPDATE_FORM = "invoices/createOrUpdateInvoiceInventory";
    private static final String VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE = VIEWS_INVOICE_CREATE_OR_UPDATE_FORM + "::editableTable";
    private static int currentInventoryPage = 1;
    private static int currentInventoryPageSize = 50;
    public InvoicesMvcController(InvoiceInventoryService invoiceService, InventoryService inventoryService) {
        this.invoiceService = invoiceService;
        this.inventoryService = inventoryService;
    }

    @RequestMapping({"/invoices/", "/invoices", "invoices", "invoices.html", "invoices/"})
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.findAll());

        return "invoices/invoices";
    }

    @RequestMapping(value = "/listInventory", method = RequestMethod.GET)
    public String listInventory(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @RequestParam("invoiceId") Long invoiceId) {

        int currentPage = page.orElse(currentInventoryPage);
        int pageSize = size.orElse(currentInventoryPageSize);
        currentInventoryPage = currentPage;

        Page<Inventory> invoiceInventoryPage = inventoryService.findInvoiceInventoryPaginated(PageRequest.of(currentPage - 1, pageSize), invoiceId);
        model.addAttribute("invoiceInventoryPage", invoiceInventoryPage);
        int totalPages = invoiceInventoryPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

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
        return "invoices/invoices";
    }

    @RequestMapping({"/find"})
    public String findInvoice(Model model) {
        model.addAttribute("invoices", invoiceService.findAll());
        return "invoices/invoices";
    }

    @GetMapping("/{id}")
    public ModelAndView showInvoice(@PathVariable("id") Long id) {
        LOGGER.info("Get /id is called! " + id);
        int currentPage = 1;
        int pageSize = currentInventoryPageSize;

        InvoiceInventory invoiceInventory = invoiceService.findById(id);
        Page<Inventory> invoiceInventoryPage = inventoryService.findInvoiceInventoryPaginated(PageRequest.of(currentPage - 1, pageSize), id);

        int totalPages = invoiceInventoryPage.getTotalPages();
        ModelAndView mav = new ModelAndView(VIEWS_INVOICE_CREATE_OR_UPDATE_FORM);
        mav.addObject("totalPages", totalPages);
        mav.addObject("invoiceInventoryPage", invoiceInventoryPage);
        mav.addObject("invoice", invoiceInventory);
        mav.addObject("totalPaidSum", invoiceService.getTotalPaidSum(invoiceInventory.getId()));
        mav.addObject("totalPaidNum", invoiceInventory.getTotalPaidNum());
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewInvoice(Model model) {
        InvoiceInventory invoiceInventory = new InvoiceInventory();
        invoiceInventory.setInventories(new ArrayList<>());
        Page<Inventory> invoiceInventoryPage = new PageImpl<>(new ArrayList<>());
        model.addAttribute("invoiceInventoryPage", invoiceInventoryPage);
        model.addAttribute("invoice", invoiceInventory);
        model.addAttribute("totalPaidSum", 0);
        model.addAttribute("totalPaidNum", invoiceInventory.getTotalPaidNum());
        return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping({"/inventory/new"})
    public String getNewInvoiceInventory(@ModelAttribute("invoice") InvoiceInventory invoiceInventory, Model model) {
        Inventory inventory = new Inventory();
        model.addAttribute("inventory", inventory);
        return VIEWS_INVOICE_INVENTORY_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid InvoiceInventory invoice, BindingResult result) {
        LOGGER.info("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {
            InvoiceInventory savedInvoice = invoiceService.save(invoice);
            return "redirect:/invoices/" + savedInvoice.getId();
        }
    }

    @PostMapping("/inventory")
    public String processCreationForm(@Valid Inventory inventory,
                                      Model model, BindingResult result) {
        LOGGER.info("Post inventory {1} is called! ", inventory.getInventoryId());
        InvoiceInventory invoiceInventory = inventory.getInvoice();
        int currentPage = currentInventoryPage;
        int pageSize = currentInventoryPageSize;
        Long invoiceId = invoiceInventory.getId();
        this.currentInventoryPage = currentPage;
        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {
            if (inventory.getInventoryId() == null && !invoiceInventory.getInventories().contains(inventory)) {
                invoiceInventory.getInventories().add(inventory);
            }
            inventory.setInvoice(invoiceInventory);

            invoiceService.save(invoiceInventory);
            inventoryService.save(inventory);

            Page<Inventory> invoiceInventoryPage = inventoryService.findInvoiceInventoryPaginated(PageRequest.of(currentPage - 1, pageSize), invoiceId);
            model.addAttribute("totalPaidSum", invoiceService.getTotalPaidSum(invoiceId));
            model.addAttribute("totalPaidNum", inventory.getInvoice().getTotalPaidNum());
            model.addAttribute("invoiceInventoryPage", invoiceInventoryPage);

            return VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE;
        }
    }

    @DeleteMapping("/inventory/{id}")
    public String processDeletionForm(@PathVariable("id") Long inventoryId,
                                      Model model) {
        LOGGER.info("Delete inventory {1} is called! ", inventoryId);
        Inventory deleteInventory = inventoryService.findById(inventoryId);

        int currentPage = currentInventoryPage;
        int pageSize = currentInventoryPageSize;
        Long invoiceId = deleteInventory.getInvoice().getId();

        InvoiceInventory invoiceInventory = deleteInventory.getInvoice();
        invoiceInventory.getInventories().remove(deleteInventory);
        inventoryService.delete(deleteInventory);
        invoiceService.save(invoiceInventory);

        Page<Inventory> invoiceInventoryPage = inventoryService.findInvoiceInventoryPaginated(PageRequest.of(currentPage - 1, pageSize), invoiceId);
        model.addAttribute("totalPaidSum", invoiceService.getTotalPaidSum(invoiceId));
        model.addAttribute("totalPaidNum", deleteInventory.getInvoice().getTotalPaidNum());
        model.addAttribute("invoiceInventoryPage", invoiceInventoryPage);
        int totalPages = invoiceInventoryPage.getTotalPages();
        model.addAttribute("totalPages", totalPages);

        return VIEWS_INVOICE_INVENTORY_EDITABLE_TABLE;
    }

    @PostMapping("/inventory/new")
    public String processCreationForm(@Valid Inventory inventory,
                                      @ModelAttribute("invoice") InvoiceInventory invoiceInventory,
                                      Model model, BindingResult result) {
        LOGGER.info("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {

            invoiceInventory.getInventories().add(inventory);
            inventory.setInvoice(invoiceInventory);

            invoiceService.save(invoiceInventory);
            inventoryService.save(inventory);

            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateInvoiceForm(@PathVariable("id") Long id, Model model) {
        InvoiceInventory invoiceInventory = invoiceService.findById(id);
        model.addAttribute("invoice", invoiceInventory);
        model.addAttribute("totalPaidSum", invoiceService.getTotalPaidSum(id));
        model.addAttribute("totalPaidNum", invoiceInventory.getTotalPaidNum());
        return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
    }

    @GetMapping("/editInventory/{index}")
    public String initUpdateInvoiceInventoryForm(@PathVariable("index") Integer index,
                                                 @ModelAttribute("invoice") InvoiceInventory invoiceInventory,
                                                 Model model) {
        model.addAttribute("inventory", invoiceInventory.getInventories().get(index));
        return VIEWS_INVOICE_INVENTORY_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/editInventory/{index}")
    public String processUpdateInvoiceInventoryForm(@Valid Inventory inventory,
                                                    @ModelAttribute("invoice") InvoiceInventory invoiceInventory,
                                                    BindingResult result, @PathVariable("index") Integer index) {
        LOGGER.info("Post {id}/editInventory is called!");

        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {
            invoiceInventory.getInventories().set(index, inventory);
            invoiceService.save(invoiceInventory);
            return "redirect:/invoices/" + invoiceInventory.getId() + "/edit";
        }
    }

    @PostMapping("/{id}/edit")
    public String processUpdateInvoiceForm(@Valid InvoiceInventory invoice, BindingResult result, @PathVariable("id") Long id) {
        LOGGER.info("Post {id}/edit is called!");

        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {
            invoice.setId(id);
            InvoiceInventory savedInvoice = invoiceService.save(invoice);
            return "redirect:/invoices/" + savedInvoice.getId();
        }
    }
}
