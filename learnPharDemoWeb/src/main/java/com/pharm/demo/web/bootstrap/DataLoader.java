package com.pharm.demo.web.bootstrap;

import com.pharm.demo.model.*;
import com.pharm.demo.repositories.CategoryMedRepository;
import com.pharm.demo.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

import static com.pharm.demo.web.util.InvoiceInventoryUtil.calculatePrice;

@Component
public class DataLoader implements CommandLineRunner {

    private final InvoiceInventoryService invoiceInventoryService;
    private final InventoryService inventoryService;
    private  final SupplierService supplierService;
    private  final MedicineService medicineService;
    private  final PharmacistService pharmacistService;
    private  final PharmUserService pharmUserService;
    private  final CategoryMedRepository categoryMedRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());


    public DataLoader(SupplierService supplierService, MedicineService medicineService,
                      PharmacistService pharmacistService, PharmUserService pharmUserService,
                      CategoryMedRepository categoryMedRepository, InventoryService inventoryService,
                      InvoiceInventoryService invoiceInventoryService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryService = invoiceInventoryService;
        this.supplierService = supplierService;
        this.medicineService = medicineService;
        this.pharmacistService = pharmacistService;
        this.pharmUserService = pharmUserService;
        this.categoryMedRepository = categoryMedRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i=0;i<1;i++) {
            Supplier supplier = new Supplier();
            supplier.setName("Astana"+i);
            supplier.setContactNumber("8777555221"+i);
            supplier.setEmail("astana@gmail.com"+i);
            supplierService.save(supplier);
        }


        Supplier supplier2 = new Supplier();
        supplier2.setName("Astana2");
        supplier2.setContactNumber("8777555222");
        supplier2.setEmail("astana2@gmail.com");
        supplierService.save(supplier2);

        Supplier supplier3 = new Supplier();
        supplier3.setName("Astana3");
        supplier3.setContactNumber("8777555223");
        supplier3.setEmail("astana3@gmail.com");
        supplierService.save(supplier3);

        Supplier supplier4 = new Supplier();
        supplier4.setName("Astana4");
        supplier4.setContactNumber("8777555224");
        supplier4.setEmail("astana4@gmail.com");
        supplierService.save(supplier4);

        Medicine medicine = new Medicine();
        CategoryMed cardioMed = new CategoryMed();
        cardioMed.setName("Cardio");
        cardioMed.setName("Cardio vuscular");
        categoryMedRepository.save(cardioMed);

        medicine.setCategory(cardioMed);
        medicine.setName("Paracetomol");
        medicineService.save(medicine);

        Medicine medicine2 =  new Medicine();
        CategoryMed brainMed = new CategoryMed();
        brainMed.setName("Brain vuscular");
        categoryMedRepository.save(brainMed);

        medicine2.setCategory(brainMed);
        medicine2.setName("Paracetomol2");
        medicineService.save(medicine2);

        Medicine medicine3 = new Medicine();
        medicine3.setCategory(cardioMed);
        medicine3.setName("Paracetomol3");
        medicineService.save(medicine3);

        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setFirstName("Aidana");
        pharmacist.setLastName("Kassimova");
        pharmacist.setUserName("aidana");
        pharmacistService.save(pharmacist);

        Pharmacist pharmacist2 = new Pharmacist();
        pharmacist2.setFirstName("Aidana");
        pharmacist2.setLastName("Kassimova");
        pharmacistService.save(pharmacist2);

        PharmUser user = new PharmUser();
        user.setUserName("admin");
        user.setPassword("admin");
        user.setRoles("ADMIN");
        user.setFirstName("admin");
        user.setLastName("admin");

        Inventory inventory = new Inventory(medicine,
                supplier2, 100, Inventory.DEFAULT_MARKUP, calculatePrice(100.0, Inventory.DEFAULT_MARKUP), 100.0, LocalDateTime.now(), pharmacist);
        Inventory inventory2 = new Inventory(medicine,
                supplier3, 10, Inventory.DEFAULT_MARKUP, calculatePrice(200.0, Inventory.DEFAULT_MARKUP), 200.0, LocalDateTime.now(), pharmacist);
        Inventory inventory3 = new Inventory(medicine,
                supplier3, 1, Inventory.DEFAULT_MARKUP, calculatePrice(300.0, Inventory.DEFAULT_MARKUP), 300.0, LocalDateTime.now(), pharmacist);

        InvoiceInventory invoice = new InvoiceInventory(null, 100, 1000.0, Arrays.asList(inventory));
        InvoiceInventory invoice2 = new InvoiceInventory(null, 10, 2000.0, Arrays.asList(inventory2));
        InvoiceInventory invoice3 = new InvoiceInventory(null, 1, 300.0, Arrays.asList(inventory3));
        inventory.setInvoice(invoice);
        inventory2.setInvoice(invoice2);
        inventory3.setInvoice(invoice3);

        invoiceInventoryService.save(invoice);
        invoiceInventoryService.save(invoice2);
        invoiceInventoryService.save(invoice3);

        if (pharmUserService.findByUserName(user.getUserName()) == null) {
            pharmUserService.save(user);
        } else {
            LOGGER.info("Such user: " + user.getUserName() + " exists already!!!!");
        }

        LOGGER.info(" Siz of users: " + pharmUserService.findAll().size());
        LOGGER.info(" Siz of inventory: " + inventoryService.findAll().size());
        LOGGER.info(" Siz of invoices: " + invoiceInventoryService.findAll().size());
        LOGGER.info(" Siz of medicines: " + medicineService.findAll().size());
        LOGGER.info(" Siz of suppliers: " + supplierService.findAll().size());
        LOGGER.info(" Siz of pharmacists: " + pharmacistService.findAll().size());

    }
}
