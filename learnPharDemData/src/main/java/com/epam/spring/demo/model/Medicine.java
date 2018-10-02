package com.epam.spring.demo.model;

public class Medicine extends BaseEntity{

    private CategoryMed categoryMed;


    public CategoryMed getCategoryMed() {
        return categoryMed;
    }

    public void setCategoryMed(CategoryMed categoryMed) {
        this.categoryMed = categoryMed;
    }

}
