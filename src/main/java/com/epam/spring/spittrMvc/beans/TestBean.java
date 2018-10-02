package com.epam.spring.spittrMvc.beans;


public class TestBean {
    private String name;
    private TestBean spouse;
    private String country;

    public TestBean(String name) {        this.name = name;    }

    public String getName() {        return name;    }
    public void setName(String name) {        this.name = name;    }

    public TestBean getSpouse() {        return spouse;    }
    public void setSpouse(TestBean spouse) {        this.spouse = spouse;    }

    public String getCountry() {        return country;    }
    public void setCountry(String country) {        this.country = country;    }
}
