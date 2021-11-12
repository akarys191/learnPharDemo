package com.pharm.demo.web.data.model;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Supplier extends Company{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplier_id_generator")
    @SequenceGenerator(name="supplier_id_generator", sequenceName = "supplier_id_seq", allocationSize=50)
    private Long id;
}
