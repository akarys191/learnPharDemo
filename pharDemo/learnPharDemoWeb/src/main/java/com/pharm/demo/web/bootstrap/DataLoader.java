package com.pharm.demo.web.bootstrap;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.pharm.demo.model.CashInventory;
import com.pharm.demo.model.CategoryMed;
import com.pharm.demo.model.Country;
import com.pharm.demo.model.Customer;
import com.pharm.demo.model.Inventory;
import com.pharm.demo.model.InventorySupplierPriceCost;
import com.pharm.demo.model.InvestedMoney;
import com.pharm.demo.model.InvoiceInventory;
import com.pharm.demo.model.InvoiceInventoryItem;
import com.pharm.demo.model.Manufacturer;
import com.pharm.demo.model.Medicine;
import com.pharm.demo.model.PharmUser;
import com.pharm.demo.model.Pharmacist;
import com.pharm.demo.model.Supplier;
import com.pharm.demo.services.CashInventoryService;
import com.pharm.demo.services.CategoryMedService;
import com.pharm.demo.services.CountryService;
import com.pharm.demo.services.CustomerService;
import com.pharm.demo.services.InventoryService;
import com.pharm.demo.services.InventorySupplierLatestService;
import com.pharm.demo.services.InvoiceInventoryItemService;
import com.pharm.demo.services.InvoiceInventoryService;
import com.pharm.demo.services.ManufacturerService;
import com.pharm.demo.services.MedicineService;
import com.pharm.demo.services.PharmUserService;
import com.pharm.demo.services.PharmacistService;
import com.pharm.demo.services.SupplierService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import static com.pharm.demo.web.util.InvoiceInventoryUtil.calculatePrice;

@Component
public class DataLoader implements CommandLineRunner {

    private final InventoryService inventoryService;
    private final CashInventoryService cashInventoryService;
    private final InvoiceInventoryService invoiceInventoryService;
    private final InvoiceInventoryItemService invoiceInventoryItemService;
    private final InventorySupplierLatestService inventorySupplierLatestService;
    private final SupplierService supplierService;
    private final MedicineService medicineService;
    private final PharmacistService pharmacistService;
    private final PharmUserService pharmUserService;
    private final CategoryMedService categoryMedService;
    private final ManufacturerService manufacturerService;
    private final CountryService countryService;
    private final CustomerService customerService;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    private Boolean load = false;

    public DataLoader(CashInventoryService cashInventoryService, SupplierService supplierService, MedicineService medicineService,
                      PharmacistService pharmacistService, PharmUserService pharmUserService,
                      CategoryMedService categoryMedService, InvoiceInventoryItemService invoiceInventoryItemService,
                      CountryService countryService, ManufacturerService manufacturerService,
                      InvoiceInventoryService invoiceInventoryService, InventoryService inventoryService,
                      CustomerService customerService, InventorySupplierLatestService inventorySupplierPriceService) {
        this.cashInventoryService = cashInventoryService;
        this.invoiceInventoryItemService = invoiceInventoryItemService;
        this.inventorySupplierLatestService = inventorySupplierPriceService;
        this.invoiceInventoryService = invoiceInventoryService;
        this.supplierService = supplierService;
        this.medicineService = medicineService;
        this.pharmacistService = pharmacistService;
        this.pharmUserService = pharmUserService;
        this.categoryMedService = categoryMedService;
        this.manufacturerService = manufacturerService;
        this.countryService = countryService;
        this.inventoryService = inventoryService;
        this.customerService = customerService;
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
                manufacturerService.saveFlush(manufacturer);
                manufacturers.add(manufacturer);
            }

            Supplier supplier2 = new Supplier();
            supplier2.setName("Astana2");
            supplier2.setContactNumber("8777555222");
            supplier2.setEmail("astana2@gmail.com");
            supplierService.saveFlush(supplier2);

            Supplier supplier3 = new Supplier();
            supplier3.setName("Astana3");
            supplier3.setContactNumber("8777555223");
            supplier3.setEmail("astana3@gmail.com");
            supplierService.saveFlush(supplier3);

            Supplier supplier4 = new Supplier();
            supplier4.setName("Astana4");
            supplier4.setContactNumber("8777555224");
            supplier4.setEmail("astana4@gmail.com");
            supplierService.saveFlush(supplier4);

            Medicine medicine = new Medicine();
            CategoryMed cardioMed = new CategoryMed();
            cardioMed.setName("Cardio");
            cardioMed.setName("Cardio vuscular");
            categoryMedService.saveFlush(cardioMed);

            medicine.setCategory(cardioMed);
            medicine.setName("Paracetomol");
            medicine.setBarCode("123456789");
            medicine.setCountry(countries.get(0));
            medicine.setManufacturer(manufacturers.get(0));
            medicineService.saveFlush(medicine);

            Medicine medicine2 = new Medicine();
            CategoryMed brainMed = new CategoryMed();
            medicine2.setBarCode("22222222222222");
            brainMed.setName("Brain vuscular");
            categoryMedService.saveFlush(brainMed);

            medicine2.setCategory(brainMed);
            medicine2.setName("Paracetomol2");
            medicine2.setCountry(countries.get(1));
            medicine2.setManufacturer(manufacturers.get(1));
            medicineService.saveFlush(medicine2);

            Medicine medicine3 = new Medicine();
            medicine3.setCategory(cardioMed);
            medicine3.setBarCode("33333333333333");
            medicine3.setName("Paracetomol3");
            medicine3.setCountry(countries.get(2));
            medicine3.setManufacturer(manufacturers.get(2));
            medicineService.saveFlush(medicine3);

            Pharmacist pharmacist = new Pharmacist();
            pharmacist.setFirstName("Aidana");
            pharmacist.setLastName("Kassimova");
            pharmacist.setUserName("aidanaKasimova");
            pharmacist.setPassword("aidanaKasimova");
            pharmacist.setRoles("ADMIN");
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

            Inventory inventory = new Inventory();
            inventory.setInventoryVersionNumber(1L);
            inventory.setMedicine(medicine);
            inventoryService.saveFlush(inventory);

            Customer customer = new Customer();
            customer.setDiscount(0.0);
            customer.setFirstName("General");
            customer.setLastName("General");
            customer.setRoles("USER");
            customerService.saveFlush(customer);

            InvoiceInventoryItem invoiceInventoryItem = new InvoiceInventoryItem(medicine,
                    supplier2, 2100.0, InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE, calculatePrice(100.0, InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE), 100.0, LocalDateTime.now(), pharmacist);
            InvoiceInventoryItem invoiceInventoryItem1 = new InvoiceInventoryItem(medicine,
                    supplier3, 10.0, InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE, calculatePrice(200.0, InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE), 200.0, LocalDateTime.now(), pharmacist2);
            InvoiceInventoryItem invoiceInventoryItem2 = new InvoiceInventoryItem(medicine,
                    supplier3, 1.0, InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE, calculatePrice(300.0, InvoiceInventoryItem.DEFAULT_MARKUP_PERCENTAGE), 300.0, LocalDateTime.now(), pharmacist3);

          InvoiceInventory invoice = new InvoiceInventory(null, supplier2, Arrays.asList(invoiceInventoryItem));
          InvoiceInventory invoice2 = new InvoiceInventory(null, supplier3, Arrays.asList(invoiceInventoryItem1));
          InvoiceInventory invoice3 = new InvoiceInventory(null, supplier4, Arrays.asList(invoiceInventoryItem2));
          invoiceInventoryItem.setInvoice(invoice);
          invoiceInventoryItem1.setInvoice(invoice2);
          invoiceInventoryItem2.setInvoice(invoice3);


          invoiceInventoryService.saveFlush(invoice);
          invoiceInventoryService.saveFlush(invoice2);
          invoiceInventoryService.saveFlush(invoice3);

          inventorySupplierLatestService.save(new InventorySupplierPriceCost());
          CashInventory cashInventory = new CashInventory();
          InvestedMoney investedMoney = new InvestedMoney(100000D, "Test origin", "Test msg", cashInventory);
          cashInventory.setTotalCashMoney(50000D);
          cashInventory.setTotalCardMoney(50000D);
          cashInventory.setInventoryVersionNumber(1L);
          cashInventory.setInvestedMonies(new ArrayList<>(
                    Collections.singletonList(investedMoney)
            ));

            cashInventoryService.saveFlush(cashInventory);

            if (pharmUserService.findByUserName(user.getUserName()) == null) {
                pharmUserService.save(user);
            } else {
                LOGGER.info("Such user: " + user.getUserName() + " exists already!!!!");
            }

            load = false;
        }

        LOGGER.info(" Is pharmacist user: " + pharmUserService.findByUserName("ADMIN"));
        LOGGER.info(" Siz of users: " + pharmUserService.findAll().size());
        LOGGER.info(" Siz of cashInventories: " + cashInventoryService.findAll().size());
        LOGGER.info(" Siz of inventory: " + invoiceInventoryItemService.findAll().size());
        LOGGER.info(" Siz of invoices: " + invoiceInventoryService.findAll().size());
        LOGGER.info(" Siz of medicines: " + medicineService.findAll().size());
        LOGGER.info(" Siz of suppliers: " + supplierService.findAll().size());
        LOGGER.info(" Siz of pharmacists: " + pharmacistService.findAll().size());

    }

    private boolean isEmpty() {
        return categoryMedService.findAll().isEmpty() ||
                medicineService.findAll().isEmpty() || supplierService.findAll().isEmpty() || pharmUserService.findAll().isEmpty() ||
                countryService.findAll().isEmpty() || manufacturerService.findAll().isEmpty() || pharmUserService.findAll().isEmpty();
    }
}
