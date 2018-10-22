package com.epam.spring.web.bootstrap;

import com.epam.spring.demo.model.CategoryMed;
import com.epam.spring.demo.model.Medicine;
import com.epam.spring.demo.model.Pharmacist;
import com.epam.spring.demo.model.Supplier;
import com.epam.spring.demo.services.MedicineService;
import com.epam.spring.demo.services.PharmacistService;
import com.epam.spring.demo.services.SupplierService;
import com.epam.spring.demo.services.map.MedicineServiceMap;
import com.epam.spring.demo.services.map.SupplierServiceMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;

@Component
public class DataLoader implements CommandLineRunner {

    private  final SupplierService supplierService;
    private  final MedicineService medicineService;
    private  final PharmacistService pharmacistService;


    public DataLoader(SupplierService supplierService, MedicineService medicineService,PharmacistService pharmacistService) {
        this.supplierService = supplierService;
        this.medicineService = medicineService;
        this.pharmacistService = pharmacistService;
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

        medicine.setCategory("Cardio");
        medicine.setName("Paracetomol");

        medicineService.save(medicine);
        Medicine medicine2 =  new Medicine();
        CategoryMed categoryMed2 = new CategoryMed();
        medicine2.setCategory("Brain");
        medicine2.setName("Paracetomol2");
        medicineService.save(medicine2);

        Medicine medicine3 = new Medicine();
        medicine3.setCategory("Cardio");
        medicine3.setName("Paracetomol3");
        medicineService.save(medicine3);

        Pharmacist pharmacist = new Pharmacist();
        pharmacist.setSchedule("1,2,3,4");
        pharmacist.setFirstName("Aidana");
        pharmacist.setLastName("Kassimova");
        pharmacistService.save(pharmacist);

        Pharmacist pharmacist2 = new Pharmacist();
        pharmacist2.setSchedule("5,6,7");
        pharmacist2.setFirstName("Aidana");
        pharmacist2.setLastName("Kassimova");
        pharmacistService.save(pharmacist2);


        System.out.println(" Siz of medicines: "+medicineService.findAll().size());
        System.out.println(" Siz of suppliers: "+supplierService.findAll().size());
        System.out.println(" Siz of pharmacists: "+pharmacistService.findAll().size());

    }
}
