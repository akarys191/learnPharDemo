package com.epam.spring.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "customers")
public class  Customer extends Person {
}
