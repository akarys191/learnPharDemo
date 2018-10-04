package com.epam.spring.web.controllers;

import com.epam.spring.demo.model.Medicine;
import com.epam.spring.demo.services.MedicineService;
import com.epam.spring.demo.services.SupplierService;
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
@RequestMapping("/medicines")
public class MedicinesController {
    private final MedicineService medicineService;

    private static final String VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM = "medicines/createOrUpdateMedicine";
    public MedicinesController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping({"/medicines/","medicines","medicines.html","medicines/"})
    public String listMedcines(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }

    @RequestMapping({"/find"})
    public String findMedicine(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }

    @GetMapping("/{medicineId}")
    public ModelAndView showMedicine(@PathVariable Long medicineId) {
        ModelAndView mav = new ModelAndView("medicines/medicineDetails");
        mav.addObject(medicineService.findById(medicineId));
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewMedicine(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM ;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Medicine medicine, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
        } else {
            Medicine saveMedicine =  medicineService.save(medicine);
            return "redirect:/medicines/" + saveMedicine.getId();
        }
    }

    @GetMapping("/{medicineId}/edit")
    public String initUpdateMedicineForm(@PathVariable Long medicineId, Model model) {
        model.addAttribute("medicine",medicineService.findById(medicineId));
        return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{medicineId}/edit")
    public String processUpdateMedicineForm(@Valid Medicine medicine, BindingResult result, @PathVariable Long medicineId) {
        if (result.hasErrors()) {
            return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
        } else {
            medicine.setId(medicineId);
            Medicine savedMedicine = medicineService.save(medicine);
            return "redirect:/medicines/" + medicine.getId();
        }
    }
}
