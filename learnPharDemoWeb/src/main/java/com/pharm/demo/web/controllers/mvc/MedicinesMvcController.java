package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Medicine;
import com.pharm.demo.services.MedicineService;
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
public class MedicinesMvcController {
    private final MedicineService medicineService;

    private static final String VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM = "medicines/createOrUpdateMedicine";
    public MedicinesMvcController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping({"/medicines/","/medicines","medicines","medicines.html","medicines/"})
    public String listMedcines(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }

    @RequestMapping({"/find"})
    public String findMedicine(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }

    @GetMapping("/{id}")
    public ModelAndView showMedicine(@PathVariable("id") Long medicineId) {
        System.out.println("Get /id is called! "+medicineId);

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
        System.out.println("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
        } else {
            Medicine saveMedicine =  medicineService.save(medicine);
            return "redirect:/medicines/" + saveMedicine.getId();
        }
    }

    @GetMapping("/{id}/edit")
    public String initUpdateMedicineForm(@PathVariable("id") Long medicineId, Model model) {
        model.addAttribute("medicine",medicineService.findById(medicineId));
        return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/{id}/edit")
    public String processUpdateMedicineForm(@Valid Medicine medicine, BindingResult result, @PathVariable("id") Long medicineId) {
        System.out.println("Post {id}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
        } else {
            //medicine.set(id);
            Medicine savedMedicine = medicineService.save(medicine);
            return "redirect:/medicines/" + medicine.getId();
        }
    }
}
