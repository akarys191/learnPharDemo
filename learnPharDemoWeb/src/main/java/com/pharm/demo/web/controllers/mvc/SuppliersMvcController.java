package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.SupplierService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

    @RequestMapping({"/find"})
    public String findSupplier(Model model){
        model.addAttribute("suppliers", supplierService.findAll() );

        return "suppliers/suppliers";
    }

    @GetMapping("/{supplierId}")
    public ModelAndView showSupplier(@PathVariable Long supplierId) {
        System.out.println("Get /supplierId is called! "+supplierId);

        ModelAndView mav = new ModelAndView("suppliers/supplierDetails");
        mav.addObject(supplierService.findById(supplierId));
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewSupplier(Model model){
        model.addAttribute("suppliers", supplierService.findAll() );

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

    @GetMapping("/{supplierId}/edit")
    public String initUpdateSupplierForm(@PathVariable Long supplierId, Model model) {
        model.addAttribute("supplier",supplierService.findById(supplierId));
        return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{supplierId}/edit")
    public String processUpdateSupplierForm(@Valid Supplier supplier, BindingResult result, @PathVariable Long supplierId) {
        System.out.println("Post {supplierId}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_SUPPLIER_CREATE_OR_UPDATE_FORM;
        } else {
            supplier.setSupplierId(supplierId);
            Supplier savedSupplier  = supplierService.save(supplier);
            return "redirect:/suppliers/" + savedSupplier.getSupplierId();
        }
    }
}
