package com.pharm.demo.dto;


import com.pharm.demo.model.CategoryMed;
import com.pharm.demo.model.CommonProperties;
import com.pharm.demo.model.Country;
import com.pharm.demo.model.Manufacturer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MedicineDTO extends CommonProperties {

    private CategoryMed category;
    private String barCode;
    private Country country;
    private Manufacturer manufacturer;
    private Integer numberOfPlates = 0;
    private Double price;
}
