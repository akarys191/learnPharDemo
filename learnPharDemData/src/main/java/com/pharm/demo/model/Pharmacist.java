package com.pharm.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "PHARMACIST")
@DiscriminatorValue("PHARMACIST")
@PrimaryKeyJoinColumn(name = "user_id")
public class Pharmacist extends PharmUser {
    @ManyToOne
    private Schedule schedule;
}
