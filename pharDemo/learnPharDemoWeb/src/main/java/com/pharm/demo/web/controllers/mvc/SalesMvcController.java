package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.web.data.dto.SalesSumsDTO;
import com.pharm.demo.web.data.model.Sales;
import com.pharm.demo.web.data.services.SalesService;
import com.pharm.demo.web.processor.context.InvoiceInventoryContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sales")
public class SalesMvcController {

    private final SalesService salesService;
    private final InvoiceInventoryContextHolder invoiceInventoryContextHolder;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String VIEWS_SALES_LIST_BY_VERSION_FORM = "sales/salesByVersion";
    private static final String VIEWS_SALES_VERSIONS_FORM = "sales/sales";

    private static int currentSalesPage = 1;
    private static int currentSalesPageSize = 50;

    public SalesMvcController(SalesService salesService, InvoiceInventoryContextHolder invoiceInventoryContextHolder) {
        this.salesService = salesService;
        this.invoiceInventoryContextHolder = invoiceInventoryContextHolder;
    }

    @RequestMapping(value = "/listSalesVersions", method = RequestMethod.GET)
    public String listSalesVersions(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(currentSalesPage);
        int pageSize = size.orElse(currentSalesPageSize);
        currentSalesPage = currentPage;
        Page<SalesSumsDTO> salesVersionPage = salesService.getTotalSoldPriceNumSumGroupByInventoryVersion(PageRequest.of(currentPage - 1, pageSize));

        int totalPages = salesVersionPage.getTotalPages();
        model.addAttribute("salesVersionPage", salesVersionPage);
        model.addAttribute("totalPages", totalPages);

        return VIEWS_SALES_VERSIONS_FORM;
    }

    @RequestMapping(value = "/version/{version}", method = RequestMethod.GET)
    public String listSales(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            @PathVariable("version") Integer version) {
        int currentPage = page.orElse(currentSalesPage);
        int pageSize = size.orElse(currentSalesPageSize);
        currentSalesPage = currentPage;
        Page<Sales> salesPage = salesService.findPaginatedByInventoryNumber(PageRequest.of(currentPage - 1, pageSize), version.longValue());
        return returnSalesPageWithAttributes(model, salesPage, version.longValue(),
                VIEWS_SALES_LIST_BY_VERSION_FORM);
    }

    @PostMapping("/sales")
    public String processCreationForm(@Valid Sales sales,
                                      Model model, BindingResult result) {
        LOGGER.info("Post sales id {} is called! ", sales.getSalesId());
        int currentPage = currentSalesPage;
        int pageSize = currentSalesPageSize;
        this.currentSalesPage = currentPage;
        if (result.hasErrors()) {
            return VIEWS_SALES_LIST_BY_VERSION_FORM;
        } else {
            salesService.save(sales);
            Page<Sales> salesPage = salesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
            model.addAttribute("salesPage", salesPage);
            return VIEWS_SALES_LIST_BY_VERSION_FORM + "::editableTable";
        }
    }

    @ResponseBody
    @DeleteMapping("/sales/{id}")
    public void processDeletionForm(@PathVariable("id") Long salesId) {
        LOGGER.info("Delete sales {} is called! ", salesId);
        salesService.deleteById(salesId);
    }

    @GetMapping("/{id}/edit")
    public String initUpdateInvoiceForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("sales", salesService.findById(id));
        return VIEWS_SALES_LIST_BY_VERSION_FORM;
    }

    private String returnSalesPageWithAttributes(Model model, Page<Sales> salesPage, Long inventoryVersionNumber,
                                                 String returnForm) {
        SalesSumsDTO salesSumsDTO = salesService.getTotalSoldPriceNumSumByInventoryVersion(
                inventoryVersionNumber
        );

        int totalPages = salesPage.getTotalPages();
        model.addAttribute("salesPage", salesPage);
        model.addAttribute("totalSoldSum", salesSumsDTO.getSumOfSold());
        model.addAttribute("totalSoldNum", salesSumsDTO.getQuantityOfSold());
        model.addAttribute("totalPages", totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return returnForm;
    }

}
