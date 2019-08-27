package com.pharm.demo.web.bootstrap;

import com.pharm.demo.model.*;
import com.pharm.demo.repositories.CategoryMedRepository;
import com.pharm.demo.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.pharm.demo.web.util.InvoiceInventoryUtil.calculatePaidSum;
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
    private final ManufacturerService manufacturerService;
    private final CountryService countryService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Boolean load = false;

    public DataLoader(SupplierService supplierService, MedicineService medicineService,
                      PharmacistService pharmacistService, PharmUserService pharmUserService,
                      CategoryMedRepository categoryMedRepository, InventoryService inventoryService,
                      CountryService countryService, ManufacturerService manufacturerService,
                      InvoiceInventoryService invoiceInventoryService) {
        this.inventoryService = inventoryService;
        this.invoiceInventoryService = invoiceInventoryService;
        this.supplierService = supplierService;
        this.medicineService = medicineService;
        this.pharmacistService = pharmacistService;
        this.pharmUserService = pharmUserService;
        this.categoryMedRepository = categoryMedRepository;
        this.manufacturerService = manufacturerService;
        this.countryService = countryService;
    }

    @Override
    public void run(String... args) throws Exception {
        List<Country> countries = new ArrayList<>();
        List<Manufacturer> manufacturers = new ArrayList<>();
        if (this.isEmpty() || load) {
            for (int i = 0; i < 5; i++) {
                Supplier supplier = new Supplier();
                supplier.setName("Supplier" + i);
                supplier.setContactNumber("8777555221" + i);
                supplier.setEmail("astana@gmail.com" + i);
                supplierService.save(supplier);
            }

            for (int i = 0; i < 5; i++) {
                Country country = new Country();
                country.setName("Country" + i);
                country.setFullName("CountryFullName" + i);
                countryService.save(country);
                countries.add(country);

            }

            for (int i = 0; i < 5; i++) {
                Manufacturer manufacturer = new Manufacturer();
                manufacturer.setName("Manufacturer" + i);
                manufacturer.setContactNumber("8777555221" + i);
                manufacturer.setEmail("manufacturer@gmail.com" + i);
                manufacturerService.save(manufacturer);
                manufacturers.add(manufacturer);

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
            medicine.setBarCode("11111111111111");
            medicine.setCountry(countries.get(0));
            medicine.setManufacturer(manufacturers.get(0));
            medicineService.save(medicine);

            Medicine medicine2 = new Medicine();
            CategoryMed brainMed = new CategoryMed();
            medicine2.setBarCode("22222222222222");
            brainMed.setName("Brain vuscular");
            categoryMedRepository.save(brainMed);

            medicine2.setCategory(brainMed);
            medicine2.setName("Paracetomol2");
            medicine2.setCountry(countries.get(1));
            medicine2.setManufacturer(manufacturers.get(1));
            medicineService.save(medicine2);

            Medicine medicine3 = new Medicine();
            medicine3.setCategory(cardioMed);
            medicine3.setBarCode("33333333333333");
            medicine3.setName("Paracetomol3");
            medicine3.setCountry(countries.get(2));
            medicine3.setManufacturer(manufacturers.get(2));
            medicineService.save(medicine3);

            Pharmacist pharmacist = new Pharmacist();
            pharmacist.setFirstName("Aidana");
            pharmacist.setLastName("Kassimova");
            pharmacist.setUserName("aidanaKasimova");
            pharmacistService.save(pharmacist);

            Pharmacist pharmacist2 = new Pharmacist();
            pharmacist2.setFirstName("Aigerim");
            pharmacist2.setLastName("Salimova");
            pharmacist2.setUserName("aigerimSalimova");
            pharmacistService.save(pharmacist2);


            Pharmacist pharmacist3 = new Pharmacist();
            pharmacist3.setFirstName("Aizhan");
            pharmacist3.setLastName("Berdeiova");
            pharmacist3.setUserName("aizhanBerdeiova");
            pharmacistService.save(pharmacist3);

            PharmUser user = new PharmUser();
            user.setUserName("admin");
            user.setPassword("admin");
            user.setRoles("ADMIN");
            user.setFirstName("admin");
            user.setLastName("admin");

            Inventory inventory = new Inventory(medicine,
                    supplier2, 100.0, Inventory.DEFAULT_MARKUP, calculatePrice(100.0, Inventory.DEFAULT_MARKUP), 100.0, LocalDateTime.now(), pharmacist);
            Inventory inventory2 = new Inventory(medicine,
                    supplier3, 10.0, Inventory.DEFAULT_MARKUP, calculatePrice(200.0, Inventory.DEFAULT_MARKUP), 200.0, LocalDateTime.now(), pharmacist);
            Inventory inventory3 = new Inventory(medicine,
                    supplier3, 1.0, Inventory.DEFAULT_MARKUP, calculatePrice(300.0, Inventory.DEFAULT_MARKUP), 300.0, LocalDateTime.now(), pharmacist);

            InvoiceInventory invoice = new InvoiceInventory(null, calculatePaidSum(inventory.getSuppliedCost(), inventory.getQuantity()), supplier2, Arrays.asList(inventory));
            InvoiceInventory invoice2 = new InvoiceInventory(null, calculatePaidSum(inventory2.getSuppliedCost(), inventory2.getQuantity()), supplier3, Arrays.asList(inventory2));
            InvoiceInventory invoice3 = new InvoiceInventory(null, calculatePaidSum(inventory3.getSuppliedCost(), inventory3.getQuantity()), supplier4, Arrays.asList(inventory3));
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

            load = false;
        }
        LOGGER.info(" Siz of users: " + pharmUserService.findAll().size());
        LOGGER.info(" Siz of inventory: " + inventoryService.findAll().size());
        LOGGER.info(" Siz of invoices: " + invoiceInventoryService.findAll().size());
        LOGGER.info(" Siz of medicines: " + medicineService.findAll().size());
        LOGGER.info(" Siz of suppliers: " + supplierService.findAll().size());
        LOGGER.info(" Siz of pharmacists: " + pharmacistService.findAll().size());

    }

    private boolean isEmpty() {
        return categoryMedRepository.findAll().isEmpty() || invoiceInventoryService.findAll().isEmpty() || invoiceInventoryService.findAll().isEmpty() ||
                medicineService.findAll().isEmpty() || supplierService.findAll().isEmpty() || pharmUserService.findAll().isEmpty() ||
                countryService.findAll().isEmpty() || manufacturerService.findAll().isEmpty() || pharmUserService.findAll().isEmpty();
    }
}
