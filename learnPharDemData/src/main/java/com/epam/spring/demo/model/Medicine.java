package com.epam.spring.demo.model;

import lombok.Builder;

public class Medicine extends BaseEntity{


    @Builder
    public Medicine(Long id, String name,String category) {
        super.setId(id);
        super.setName(name);
        this.category = category;
    }

    private String category;


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


}
