package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.Medicine;
import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.MedicineService;
import com.pharm.demo.services.PharmacistService;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/inventory")
public class InventoryMvcController {
    private final InventoryService inventoryService;
    private final MedicineService medicineService;
    private final SupplierService supplierService;
    private final PharmacistService pharmacistService;

    private static final String VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM = "inventory/createOrUpdateInventory";

    public InventoryMvcController(InventoryService inventoryService, MedicineService medicineService,
                                  SupplierService supplierService, PharmacistService pharmacistService) {
        this.inventoryService = inventoryService;
        this.medicineService = medicineService;
        this.supplierService = supplierService;
        this.pharmacistService = pharmacistService;
    }

    @RequestMapping({"/inventory/", "/inventory", "inventory", "inventory.html", "inventory/"})
    public String listinventory(Model model) {
        model.addAttribute("inventory", inventoryService.findAll());

        return "inventory/inventory";
    }

    @RequestMapping(value = "/listInventory", method = RequestMethod.GET)
    public String listinventory(
            Model model,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(50);

        Page<Inventory> inventoryPage = inventoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

        model.addAttribute("inventoryPage", inventoryPage);

        int totalPages = inventoryPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "inventory/inventory";
    }

    @RequestMapping({"/find"})
    public String findInventory(Model model) {
        model.addAttribute("inventory", inventoryService.findAll());

        return "inventory/inventory";
    }

    @GetMapping("/{inventoryId}")
    public ModelAndView showInventory(@PathVariable("inventoryId") Long inventoryId) {
        System.out.println("Get /inventoryId is called! " + inventoryId);

        ModelAndView mav = new ModelAndView("inventory/inventoryDetails");
        mav.addObject(inventoryService.findById(inventoryId));
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewInventory(Model model) {
        model.addAttribute("inventories", inventoryService.findAll());
        model.addAttribute("inventory", new Inventory());
        return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid Inventory inventory, BindingResult result) {
        System.out.println("Post new is called! ");

        if (result.hasErrors()) {
            return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
        } else {
            Inventory savedInventory = inventoryService.save(inventory);
            return "redirect:/inventory/" + savedInventory.getInventoryId();
        }
    }

    @GetMapping("/{inventoryId}/edit")
    public String initUpdateInventoryForm(@PathVariable("inventoryId") Long inventoryId, Model model) {
        model.addAttribute("inventory", inventoryService.findById(inventoryId));
        return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
    }

    @ModelAttribute("medicines")
    public Set<Medicine> getMedicines() {
        Set<Medicine> medicineSet = medicineService.findAll();
        return medicineSet;
    }

    @ModelAttribute("suppliers")
    public Set<Supplier> getSuppliers() {
        Set<Supplier> supplierSet = supplierService.findAll();
        return supplierSet;
    }

    @ModelAttribute("acceptingPharmacists")
    public Set<Pharmacist> getPharmacists() {
        Set<Pharmacist> pharmacistSet = pharmacistService.findAll();
        return pharmacistSet;
    }

    @PostMapping("/{inventoryId}/edit")
    public String processUpdateInventoryForm(@Valid Inventory inventory, BindingResult result, @PathVariable("inventoryId") Long inventoryId) {
        System.out.println("Post {inventoryId}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
        } else {
            inventory.setInventoryId(inventoryId);
            Inventory savedInventory = inventoryService.save(inventory);
            return "redirect:/inventory/" + savedInventory.getInventoryId();
        }
    }
}
