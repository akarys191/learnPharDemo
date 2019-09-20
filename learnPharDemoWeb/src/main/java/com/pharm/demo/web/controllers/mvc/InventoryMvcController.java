package com.pharm.demo.web.controllers.mvc;

import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.services.InventoryService;
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
@RequestMapping("/inventory")
public class InventoryMvcController {
    private final InventoryService inventoryService;


    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private static final String VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM = "inventory/createOrUpdateInventory";

    public InventoryMvcController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
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

        Page<InvoiceInventoryItem> inventoryPage = inventoryService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

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
        LOGGER.info("Get /inventoryId is called! " + inventoryId);

        ModelAndView mav = new ModelAndView("inventory/inventoryDetails");
        mav.addObject(inventoryService.findById(inventoryId));
        return mav;
    }

    @GetMapping({"/new"})
    public String getNewInventory(Model model) {
        model.addAttribute("inventories", inventoryService.findAll());
        model.addAttribute("inventory", new InvoiceInventoryItem());
        return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("/new")
    public String processCreationForm(@Valid InvoiceInventoryItem inventory, BindingResult result) {
        LOGGER.info("Post new is called! ");
        if (result.hasErrors()) {
            return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
        } else {
            InvoiceInventoryItem savedInventory = inventoryService.save(inventory);
            return "redirect:/inventory/" + savedInventory.getInventoryId();
        }

    }

    @GetMapping("/{inventoryId}/edit")
    public String initUpdateInventoryForm(@PathVariable("inventoryId") Long inventoryId, Model model) {
        model.addAttribute("inventory", inventoryService.findById(inventoryId));
        return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
    }


    @PostMapping("/{inventoryId}/edit")
    public String processUpdateInventoryForm(@Valid InvoiceInventoryItem inventory, BindingResult result, @PathVariable("inventoryId") Long inventoryId) {
        LOGGER.info("Post {inventoryId}/edit is called! ");

        if (result.hasErrors()) {
            return VIEWS_INVENTORY_CREATE_OR_UPDATE_FORM;
        } else {
            inventory.setInventoryId(inventoryId);
            InvoiceInventoryItem savedInventory = inventoryService.save(inventory);
            return "redirect:/inventory/" + savedInventory.getInventoryId();
        }
    }
}
