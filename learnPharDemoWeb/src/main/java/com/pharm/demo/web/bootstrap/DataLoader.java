package com.pharm.demo.web.bootstrap;

import com.pharm.demo.model.*;
import com.pharm.demo.repositories.CategoryMedRepository;
import com.pharm.demo.services.MedicineService;
import com.pharm.demo.services.PharmUserService;
import com.pharm.demo.services.PharmacistService;
import com.pharm.demo.services.SupplierService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private  final SupplierService supplierService;
    private  final MedicineService medicineService;
    private  final PharmacistService pharmacistService;
    private  final PharmUserService pharmUserService;
    private  final CategoryMedRepository categoryMedRepository;


    public DataLoader(SupplierService supplierService, MedicineService medicineService,
                      PharmacistService pharmacistService, PharmUserService pharmUserService,
                      CategoryMedRepository categoryMedRepository) {
        this.supplierService = supplierService;
        this.medicineService = medicineService;
        this.pharmacistService = pharmacistService;
        this.pharmUserService = pharmUserService;
        this.categoryMedRepository = categoryMedRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Supplier supplier = new Supplier();
        supplier.setName("Astana");
        supplier.setContactNumber("8777555221");
        supplier.setEmail("astana@gmail.com");
        supplierService.save(supplier);

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
        pharmacistService.save(pharmacist);

        Pharmacist pharmacist2 = new Pharmacist();
        pharmacist2.setFirstName("Aidana");
        pharmacist2.setLastName("Kassimova");
        pharmacistService.save(pharmacist2);

        PharmUser user = new PharmUser();
        user.setUserName("admin");
        user.setPassword("MAJ19911004");
        user.setRoles("ADMINISTRATOR");
        user.setFirstName("admin");
        user.setLastName("admin");
        pharmUserService.save(user);

        System.out.println(" Siz of medicines: "+medicineService.findAll().size());
        System.out.println(" Siz of suppliers: "+supplierService.findAll().size());
        System.out.println(" Siz of pharmacists: "+pharmacistService.findAll().size());

    }
}
