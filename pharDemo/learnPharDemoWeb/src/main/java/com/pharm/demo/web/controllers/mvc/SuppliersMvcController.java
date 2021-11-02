package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.SupplierService;
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
@RequestMapping("/suppliers")
public class SuppliersMvcController {
    private final SupplierService supplierService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM = "suppliers/createOrUpdateSupplier";

    public SuppliersMvcController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @RequestMapping({"/suppliers/", "/suppliers", "suppliers", "inventory.html", "suppliers/"})
    public String listSuppliers(Model model){
        model.addAttribute("suppliers", supplierService.findAll() );

        return "suppliers/suppliers";
    }

    @RequestMapping(value = "/listSuppliers", method = RequestMethod.GET)
    public String listSuppliers(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);

        Page<Supplier> supplierPage = supplierService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("supplierPage", supplierPage);

        int totalPages = supplierPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "suppliers/suppliers";
    }

    @RequestMapping({"/find"})
    public String findSupplier(Model model){
        model.addAttribute("suppliers", supplierService.findAll() );

        return "suppliers/suppliers";
    }

    @GetMapping("/{id}")
    public ModelAndView showSupplier(@PathVariable("id") Long id) {
        LOGGER.info("Get /id is called! " + id);

        ModelAndView mav = new ModelAndView("suppliers/supplierDetails");
        mav.addObject(supplierService.findById(id));
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewSupplier(Model model){
        model.addAttribute("suppliers", supplierService.findAll() );
        model.addAttribute("supplier", new Supplier());
        return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM ;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Supplier supplier, BindingResult result) {
        LOGGER.info("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
        } else {
            Supplier savedSupplier =  supplierService.save(supplier);
            return "redirect:/suppliers/" + savedSupplier.getId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateSupplierForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("supplier", supplierService.findById(id));
        return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateSupplierForm(@Valid Supplier supplier, BindingResult result, @PathVariable("id") Long id) {
        LOGGER.info("Post {id}/edit is called!");

        if (result.hasErrors()) {
            return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
        } else {
            supplier.setId(id);
            Supplier savedSupplier  = supplierService.save(supplier);
            return "redirect:/suppliers/" + savedSupplier.getId();
        }
    }
}
