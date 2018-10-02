package com.epam.spring.web.bootstrap;

import com.epam.spring.demo.model.CategoryMed;
import com.epam.spring.demo.model.Medicine;
import com.epam.spring.demo.model.Supplier;
import com.epam.spring.demo.services.MedicineService;
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


    public DataLoader(SupplierService supplierService, MedicineService medicineService) {
        this.supplierService = supplierService;
        this.medicineService = medicineService;
    }

    @Override
    public void run(String... args) throws Exception {
        Supplier supplier = new Supplier();
        supplier.setAddress("Astana");
        supplier.setContactNumber("8777555221");
        supplier.setEmail("astana@gmail.com");
        supplier.setId(1L);
        supplierService.save(supplier);

        Supplier supplier2 = new Supplier();
        supplier.setAddress("Astana2");
        supplier.setContactNumber("8777555222");
        supplier.setEmail("astana2@gmail.com");
        supplier.setId(1L);

        supplierService.save(supplier2);

        Supplier supplier3 = new Supplier();
        supplier.setAddress("Astana3");
        supplier.setContactNumber("8777555223");
        supplier.setEmail("astana3@gmail.com");
        supplier.setId(1L);


        supplierService.save(supplier3);

        Supplier supplier4 = new Supplier();
        supplier.setAddress("Astana4");
        supplier.setContactNumber("8777555224");
        supplier.setEmail("astana4@gmail.com");
        supplier.setId(1L);


        supplierService.save(supplier4);

        Medicine medicine = new Medicine();
        CategoryMed categoryMed = new CategoryMed();
        categoryMed.setId(1L);
        categoryMed.setName("Cardio");
        medicine.setCategoryMed(categoryMed);
        medicine.setId(1L);
        medicine.setName("Paracetomol");

        medicineService.save(medicine);
        Medicine medicine2 = new Medicine();
        CategoryMed categoryMed2 = new CategoryMed();
        categoryMed2.setId(2L);
        categoryMed.setName("Brain");
        medicine.setCategoryMed(categoryMed2);
        medicine.setId(2L);
        medicine.setName("Paracetomol2");
        medicineService.save(medicine2);

        Medicine medicine3 = new Medicine();
        medicine.setCategoryMed(categoryMed);
        medicine.setId(3L);
        medicine.setName("Paracetomol3");
        medicineService.save(medicine3);

        System.out.println(" Siz of medicines: "+medicineService.findAll().size());
        System.out.println(" Siz of suppliers: "+supplierService.findAll().size());

    }
}
