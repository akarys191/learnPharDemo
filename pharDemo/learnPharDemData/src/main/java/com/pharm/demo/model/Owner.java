package com.pharm.demo.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "OWNER")
@DiscriminatorValue("OWNER")
@PrimaryKeyJoinColumn(name = "user_id")
public class Owner extends PharmUser {
}
