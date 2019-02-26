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
@RequestMapping("/mvc/medicines")
public class MedicinesMvcController {
    private final MedicineService medicineService;

    private static final String VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM = "medicines/createOrUpdateMedicine";
    public MedicinesMvcController(MedicineService medicineService) {
        this.medicineService = medicineService;
    }

    @RequestMapping({"/medicines/","/medicines","medicines","medicines.html","medicines/"})
    public String listMedcines(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "mvc/medicines/medicines";
    }

    @RequestMapping({"/find"})
    public String findMedicine(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "mvc/medicines/medicines";
    }

    @GetMapping("/{medicineId}")
    public ModelAndView showMedicine(@PathVariable Long medicineId) {
        System.out.println("Get /medicineId is called! "+medicineId);

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
            return "redirect:/mvc/medicines/" + saveMedicine.getMedicineId();
        }
    }

    @GetMapping("/{medicineId}/edit")
    public String initUpdateMedicineForm(@PathVariable Long medicineId, Model model) {
        model.addAttribute("medicine",medicineService.findById(medicineId));
        return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/mvc/{medicineId}/edit")
    public String processUpdateMedicineForm(@Valid Medicine medicine, BindingResult result, @PathVariable Long medicineId) {
        System.out.println("Post {medicineId}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
        } else {
            //medicine.set(medicineId);
            Medicine savedMedicine = medicineService.save(medicine);
            return "redirect:/mvc/medicines/" + medicine.getMedicineId();
        }
    }
}
