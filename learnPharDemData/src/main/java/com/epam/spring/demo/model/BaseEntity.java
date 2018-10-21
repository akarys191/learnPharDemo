package com.epam.spring.demo.model;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity {

    protected Long id;
    private String name;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
