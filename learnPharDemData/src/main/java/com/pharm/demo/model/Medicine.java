package com.pharm.demo.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Medicine extends CommonProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "medicine_id_generator")
    @SequenceGenerator(name="medicine_id_generator", sequenceName = "medicine_id_seq", allocationSize=50)
    private Long id;
    @ManyToOne
    private CategoryMed category;

    @ManyToOne
    private Country country;
    @ManyToOne
    private Manufacturer manufacturer;
    private Integer numberOfPlates = 0;
}
