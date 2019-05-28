package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.CategoryMed;
import com.pharm.demo.model.Medicine;
import com.pharm.demo.services.CategoryMedService;
import com.pharm.demo.services.MedicineService;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/medicines")
public class MedicinesMvcController {
    private final MedicineService medicineService;
    private final CategoryMedService categoryMedService;

    private static final String VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM = "medicines/createOrUpdateMedicine";

    public MedicinesMvcController(MedicineService medicineService, CategoryMedService categoryMedService) {
        this.medicineService = medicineService;
        this.categoryMedService = categoryMedService;
    }

    @RequestMapping({"/medicines/","/medicines","medicines","medicines.html","medicines/"})
    public String listMedcines(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }

    @RequestMapping(value = "/listMedicines", method = RequestMethod.GET)
    public String listMedicines(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(15);

        Page<Medicine> medicinePage = medicineService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("medicinePage", medicinePage);

        int totalPages = medicinePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "medicines/medicines";
    }

    @RequestMapping({"/find"})
    public String findMedicine(Model model){
        model.addAttribute("medicines", medicineService.findAll() );

        return "medicines/medicines";
    }

    @GetMapping("/{userId}")
    public ModelAndView showMedicine(@PathVariable("userId") Long medicineId) {
        System.out.println("Get /userId is called! "+medicineId);

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
            return "redirect:/medicines/" + saveMedicine.getMedicineId();
        }
    }

    @GetMapping("/{userId}/edit")
    public String initUpdateMedicineForm(@PathVariable("userId") Long medicineId, Model model) {
        model.addAttribute("medicine",medicineService.findById(medicineId));
        return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
    }

    @ModelAttribute("categories")
    public Set<CategoryMed> getCategories() {
        Set<CategoryMed> categoryMedSet = categoryMedService.findAll();
        return categoryMedSet;
    }

    @PostMapping("/{userId}/edit")
    public String processUpdateMedicineForm(@Valid Medicine medicine, BindingResult result, @PathVariable("userId") Long medicineId) {
        System.out.println("Post {userId}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_MEDICINE_CREATE_OR_UPDATE_FORM;
        } else {
            //medicine.set(userId);
            Medicine savedMedicine = medicineService.save(medicine);
            return "redirect:/medicines/" + medicine.getMedicineId();
        }
    }
}
