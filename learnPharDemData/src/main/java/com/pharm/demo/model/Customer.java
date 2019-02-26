package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class  Customer extends PersonAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
    @SequenceGenerator(name="customer_id_generator", sequenceName = "customer_id_seq", allocationSize=50)
    private Long customerId;
}
