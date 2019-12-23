package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.dto.CashRegistrySalesSumsDTO;
import com.pharm.demo.model.CashRegistry;
import com.pharm.demo.model.Sales;
import com.pharm.demo.services.CashRegistryService;
import com.pharm.demo.services.SalesService;
import com.pharm.demo.web.processor.CashRegistryProcessor;
import com.pharm.demo.web.processor.context.CashRegistryContextHolder;
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
@RequestMapping("/cashRegistry")
public class CashRegistryMvcController {

    private final CashRegistryService cashRegistryService;
    private final CashRegistryProcessor cashRegistryProcessor;
    private final CashRegistryContextHolder cashRegistryContextHolder;
    private final SalesService salesService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM = "cashRegistry/cashRegistry";

    private static int currentCashRegistrySalesPage = 1;
    private static int currentCashRegistrySalesPageSize = 50;

    public CashRegistryMvcController(CashRegistryService cashRegistryService, CashRegistryProcessor cashRegistryProcessor, CashRegistryContextHolder cashRegistryContextHolder, SalesService salesService) {
        this.cashRegistryService = cashRegistryService;
        this.cashRegistryProcessor = cashRegistryProcessor;
        this.cashRegistryContextHolder = cashRegistryContextHolder;
        this.salesService = salesService;
    }

    @RequestMapping({"/cashRegistry/", "/cashRegistry", "cashRegistry", "cashRegistry.html", "cashRegistry/"})
    public String listCashRegistry(Model model) {
        model.addAttribute("cashRegistry", cashRegistryService.findAll());

        return "cashRegistry/cashRegistry";
    }

    @RequestMapping(value = "/listCashRegistrySales", method = RequestMethod.GET)
    public String listCashRegistrySales(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {

        int currentPage = page.orElse(currentCashRegistrySalesPage);
        int pageSize = size.orElse(currentCashRegistrySalesPageSize);
        CashRegistry activeCashRegistry = cashRegistryContextHolder.getCashRegistryForToday();
        currentCashRegistrySalesPage = currentPage;
        Page<Sales> cashRegistrySalesPage = salesService.findPaginateByCashRegistry(PageRequest.of(currentPage - 1, pageSize), activeCashRegistry.getCashRegistryId());

        int totalPages = cashRegistrySalesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return returnCashRegistryPage(model, activeCashRegistry, cashRegistrySalesPage,
                VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String processCreationForm(@Valid CashRegistry cashRegistry,
                                      Model model, BindingResult result) {
        LOGGER.info("Post cashRegistry id {} is called! ", cashRegistry.getCashRegistryId());
        int currentPage = currentCashRegistrySalesPage;
        int pageSize = currentCashRegistrySalesPageSize;
        this.currentCashRegistrySalesPage = currentPage;
        if (result.hasErrors()) {
            return VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM;
        } else {
            cashRegistryService.save(cashRegistry);
            Page<Sales> cashRegistrySalesPage = salesService.findPaginateByCashRegistry(PageRequest.of(currentPage - 1, pageSize), cashRegistry.getCashRegistryId());
            model.addAttribute("cashRegistrySalesPage", cashRegistrySalesPage);
            return VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM + "::editableTable";
        }
    }

    @RequestMapping(value = "/sales/{id}", method = RequestMethod.DELETE)
    public String processDeletionForm(@PathVariable("id") Long salesId,
                                      Model model) {
        LOGGER.info("Delete cashRegistry sales {} is called! ", salesId);
        CashRegistry activeCashRegistry = cashRegistryContextHolder.getCashRegistryForToday();
        Sales deleteSales = salesService.findById(salesId);
        cashRegistryProcessor.processDeleteCashRegistrySales(deleteSales);
        int currentPage = currentCashRegistrySalesPage;
        int pageSize = currentCashRegistrySalesPageSize;
        Page<Sales> cashRegistrySalesPage = salesService.findPaginateByCashRegistry(PageRequest.of(currentPage - 1, pageSize), activeCashRegistry.getCashRegistryId());
        return returnCashRegistryPage(model, activeCashRegistry, cashRegistrySalesPage,
                VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM + "::editableTable");
    }

    @RequestMapping(value = "/sales", method = RequestMethod.POST)
    public String processCreationForm(@Valid Sales cashRegistrySales,
                                      Model model, BindingResult result) {
        LOGGER.info("Post cashRegistry Sales id {} is called! ", cashRegistrySales.getSalesId());
        if (result.hasErrors()) {
            return VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM;
        } else {
            CashRegistry activeCashRegistry = cashRegistryContextHolder.getCashRegistryForToday();
            cashRegistryProcessor.processSaveCashRegistrySales(cashRegistrySales);
            salesService.save(cashRegistrySales);
            int currentPage = currentCashRegistrySalesPage;
            int pageSize = currentCashRegistrySalesPageSize;
            Page<Sales> cashRegistrySalesPage = salesService.findPaginateByCashRegistry(PageRequest.of(currentPage - 1, pageSize), activeCashRegistry.getCashRegistryId());
            return returnCashRegistryPage(model, activeCashRegistry, cashRegistrySalesPage,
                    VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM + "::editableTable");
        }
    }

    @ResponseBody
    @DeleteMapping("/cashRegistry/{id}")
    public void processDeletionForm(@PathVariable("id") Long cashRegistryId) {
        LOGGER.info("Delete cashRegistry {} is called! ", cashRegistryId);
        cashRegistryService.deleteById(cashRegistryId);
    }

    @GetMapping("/{id}/edit")
    public String initUpdateInvoiceForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("cashRegistry", cashRegistryService.findById(id));
        return VIEWS_CASHREGISTRY_CREATE_OR_UPDATE_FORM;
    }

    private String returnCashRegistryPage(Model model, CashRegistry activeCashRegistry, Page<Sales> cashRegistrySalesPage,
                                          String returnForm) {
        model.addAttribute("cashRegistryId", activeCashRegistry.getCashRegistryId());
        CashRegistrySalesSumsDTO cashRegistrySalesSumsDTO = salesService.getTotalSoldPriceNumSum(activeCashRegistry.getCashRegistryId());
        model.addAttribute("totalSoldSum", cashRegistrySalesSumsDTO.getSumOfSold());
        model.addAttribute("totalSoldNum", cashRegistrySalesSumsDTO.getQuantityOfSold());
        model.addAttribute("cashRegistrySalesPage", cashRegistrySalesPage);
        return returnForm;
    }

}
