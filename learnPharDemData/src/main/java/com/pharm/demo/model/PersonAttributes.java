package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class PersonAttributes {
    private String firstName;
    private String lastName;
    private String address;
}
