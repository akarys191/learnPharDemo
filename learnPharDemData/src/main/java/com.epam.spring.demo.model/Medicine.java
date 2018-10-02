package com.epam.spring.demo.model;

import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

public class Medicine extends BaseEntity{

    private CategoryMed categoryMed;


    public CategoryMed getCategoryMed() {
        return categoryMed;
    }

    public void setCategoryMed(CategoryMed categoryMed) {
        this.categoryMed = categoryMed;
    }

}
