package com.pharm.demo.model;

//import javax.persistence.Entity;
//import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Company extends CommonProperties {
    private String address;
    private String contactNumber;
    private String email;
}
