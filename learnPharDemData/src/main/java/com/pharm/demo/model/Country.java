package com.pharm.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country extends CommonProperties {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "country_id_generator")
    @SequenceGenerator(name = "country_id_generator", sequenceName = "country_id_seq", allocationSize = 50)
    private Long id;
}

