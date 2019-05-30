package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.SupplierService;
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

    private static final String VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM = "suppliers/createOrUpdateSupplier";
    public SuppliersMvcController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @RequestMapping({"/suppliers/","/suppliers","suppliers","suppliers.html","suppliers/"})
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

    @GetMapping("/{userId}")
    public ModelAndView showSupplier(@PathVariable("userId") Long supplierId) {
        System.out.println("Get /userId is called! "+supplierId);

        ModelAndView mav = new ModelAndView("suppliers/supplierDetails");
        mav.addObject(supplierService.findById(supplierId));
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
        System.out.println("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
        } else {
            Supplier savedSupplier =  supplierService.save(supplier);
            return "redirect:/suppliers/" + savedSupplier.getSupplierId();
        }
    }

    @GetMapping("/{userId}/edit")
    public String initUpdateSupplierForm(@PathVariable("userId") Long supplierId, Model model) {
        model.addAttribute("supplier",supplierService.findById(supplierId));
        return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{userId}/edit")
    public String processUpdateSupplierForm(@Valid Supplier supplier, BindingResult result, @PathVariable("userId") Long supplierId) {
        System.out.println("Post {userId}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
        } else {
            supplier.setSupplierId(supplierId);
            Supplier savedSupplier  = supplierService.save(supplier);
            return "redirect:/suppliers/" + savedSupplier.getSupplierId();
        }
    }
}
