package com.pharm.demo.web.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class CommonProperties extends AbstractEntity {
    private String name;
    private String fullName;
}
