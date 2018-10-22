package com.epam.spring.demo.model;


import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "owners")
public class Owner extends Person {
}
