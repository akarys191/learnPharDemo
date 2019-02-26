package com.pharm.demo.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Owner extends PersonAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "owner_id_generator")
    @SequenceGenerator(name="owner_id_generator", sequenceName = "owner_id_seq", allocationSize=50)
    private Long ownerId;
}
