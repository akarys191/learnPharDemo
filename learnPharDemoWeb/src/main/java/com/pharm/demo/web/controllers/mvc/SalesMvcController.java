package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Sales;
import com.pharm.demo.services.SalesService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sales")
public class SalesMvcController {

    private final SalesService salesService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String VIEWS_SALES_CREATE_OR_UPDATE_FORM = "sales/sales";

    private static int currentSalesPage = 1;
    private static int currentSalesPageSize = 50;

    public SalesMvcController(SalesService salesService) {
        this.salesService = salesService;
    }

    @RequestMapping({"/sales/", "/sales", "sales", "cashRegistry.html", "sales/"})
    public String listSales(Model model) {
        model.addAttribute("sales", salesService.findAll());

        return "sales/sales";
    }

    @RequestMapping(value = "/listSales", method = RequestMethod.GET)
    public String listSales(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(currentSalesPage);
        int pageSize = size.orElse(currentSalesPageSize);
        currentSalesPage = currentPage;
        Page<Sales> salesPage = salesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("salesPage", salesPage);

        int totalPages = salesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return VIEWS_SALES_CREATE_OR_UPDATE_FORM;
    }

    @RequestMapping({"/find"})
    public String findInvoice(Model model) {
        model.addAttribute("sales", salesService.findAll());

        return "sales/sales";
    }

    @GetMapping("/{id}")
    public ModelAndView showSale(@PathVariable("id") Long id) {
        LOGGER.info("Get /id is called! " + id);
        int currentPage = 1;
        int pageSize = 50;

        Sales sales = salesService.findById(id);
        Page<Sales> salesPage = salesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        ModelAndView mav = new ModelAndView(VIEWS_SALES_CREATE_OR_UPDATE_FORM);
        mav.addObject("salesPage", salesPage);
        mav.addObject("sales", sales);
        return mav;
    }

    @PostMapping("/sales")
    public String processCreationForm(@Valid Sales sales,
                                      Model model, BindingResult result) {
        LOGGER.info("Post sales id {1} is called! ", sales.getSalesId());
        int currentPage = currentSalesPage;
        int pageSize = currentSalesPageSize;
        this.currentSalesPage = currentPage;
        if (result.hasErrors()) {
            return VIEWS_SALES_CREATE_OR_UPDATE_FORM;
        } else {
            salesService.save(sales);
            Page<Sales> salesPage = salesService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
            model.addAttribute("salesPage", salesPage);
            return VIEWS_SALES_CREATE_OR_UPDATE_FORM + "::editableTable";
        }
    }

    @ResponseBody
    @DeleteMapping("/sales/{id}")
    public void processDeletionForm(@PathVariable("id") Long salesId) {
        LOGGER.info("Delete sales {1} is called! ", salesId);
        salesService.deleteById(salesId);
    }

    @GetMapping("/{id}/edit")
    public String initUpdateInvoiceForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("sales", salesService.findById(id));
        return VIEWS_SALES_CREATE_OR_UPDATE_FORM;
    }
}
