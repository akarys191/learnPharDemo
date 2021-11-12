package com.pharm.demo.web.data.model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public class Company extends CommonProperties {
    private Long bin;
    private String address;
    private String contactNumber;
    private String email;
}
