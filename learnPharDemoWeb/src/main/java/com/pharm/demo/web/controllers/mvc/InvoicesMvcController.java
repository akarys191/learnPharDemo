package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InvoiceInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.pharm.demo.web.util.InvoiceInventoryUtil.calculatePaidSum;
import static com.pharm.demo.web.util.InvoiceInventoryUtil.increasePaidSum;

@Controller
@RequestMapping("/invoices")
@SessionAttributes("invoice")
public class InvoicesMvcController {

    private final InvoiceInventoryService invoiceService;
    private final InventoryService inventoryService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final String VIEWS_INVOICE_CREATE_OR_UPDATE_FORM = "/invoices/createOrUpdateInvoice";
    private static final String VIEWS_INVOICE_INVENTORY_CREATE_OR_UPDATE_FORM = "invoices/createOrUpdateInvoiceInventory";

    public InvoicesMvcController(InvoiceInventoryService invoiceService, InventoryService inventoryService) {
        this.invoiceService = invoiceService;
        this.inventoryService = inventoryService;
    }

    @RequestMapping({"/invoices/", "/invoices", "invoices", "invoices.html", "invoices/"})
    public String listInvoices(Model model) {
        model.addAttribute("invoices", invoiceService.findAll());

        return "invoices/invoices";
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
    public ModelAndView showInvoice(@PathVariable("id") Long id) {
        LOGGER.info("Get /id is called! " + id);
        InvoiceInventory invoiceInventory = invoiceService.findById(id);
        ModelAndView mav = new ModelAndView(VIEWS_INVOICE_CREATE_OR_UPDATE_FORM);
        mav.addObject("invoice", invoiceInventory);
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewInvoice(Model model) {
        InvoiceInventory invoiceInventory = new InvoiceInventory();
        invoiceInventory.setInventories(new ArrayList<>());
        InvoiceInventory savedInvoice = invoiceService.save(invoiceInventory);
        model.addAttribute("invoice", savedInvoice);
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

    @PostMapping("/inventory/new")
    public String processCreationForm(@Valid Inventory inventory,
                                      @ModelAttribute("invoice") InvoiceInventory invoiceInventory,
                                      Model model, BindingResult result) {
        LOGGER.info("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        } else {
            invoiceInventory.getInventories().add(inventory);

            Double calculatedPaidSum = calculatePaidSum(inventory.getSuppliedCost(), inventory.getQuantity());

            invoiceInventory.setPaidSum(increasePaidSum(invoiceInventory.getPaidSum(), calculatedPaidSum));
            inventory.setInvoice(invoiceInventory);

            inventoryService.save(inventory);
            invoiceService.save(invoiceInventory);
            return VIEWS_INVOICE_CREATE_OR_UPDATE_FORM;
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateInvoiceForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("invoice", invoiceService.findById(id));
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
