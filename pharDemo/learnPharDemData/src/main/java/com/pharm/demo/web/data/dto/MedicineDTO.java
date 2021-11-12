package com.pharm.demo.web.data.dto;


import com.pharm.demo.web.data.model.CategoryMed;
import com.pharm.demo.web.data.model.CommonProperties;
import com.pharm.demo.web.data.model.Country;
import com.pharm.demo.web.data.model.Manufacturer;
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
