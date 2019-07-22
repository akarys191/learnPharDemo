package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Manufacturer extends Company {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "manufacturer_id_generator")
    @SequenceGenerator(name = "manufacturer_id_generator", sequenceName = "manufacturer_id_seq", allocationSize = 50)
    private Long id;

    @ManyToOne
    private Country country;
}
