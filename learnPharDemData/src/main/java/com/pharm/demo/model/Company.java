package com.pharm.demo.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Company extends CommonProperties {
    private String address;
    private String contactNumber;
    private String email;
}
