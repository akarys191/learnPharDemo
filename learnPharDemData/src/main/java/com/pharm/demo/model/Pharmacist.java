package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class Pharmacist extends PersonAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pharmacist_id_generator")
    @SequenceGenerator(name="pharmacist_id_generator", sequenceName = "pharmacist_id_seq", allocationSize=50)
    private Long pharmacistId;
    @ManyToOne
    private Schedule schedule;
}
