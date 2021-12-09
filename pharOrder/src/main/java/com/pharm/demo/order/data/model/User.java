package com.pharm.demo.order.data.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "USER")
@Setter
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "sur_name", nullable = false)
    private String surName;
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Column(name = "email", nullable = false)
    private String email;
    @OneToMany(mappedBy="user")
    @Column(name = "order_ser", nullable = false)
    private Set<Order> orderSet;

    public User(String name, String surName, String phoneNumber, String email, Set<Order> orderSet) {
        this.name = name;
        this.surName = surName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.orderSet = orderSet;
    }
}
