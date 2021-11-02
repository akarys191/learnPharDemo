package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "CUSTOMER")
@DiscriminatorValue("CUSTOMER")
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends PharmUser {
    Double discount;
}
