package com.pharm.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class CategoryMed extends CommonProperties{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_med_id_generator")
    @SequenceGenerator(name="category_med_id_generator", sequenceName = "category_med_id_seq", allocationSize=50)
    private Long id;
}
