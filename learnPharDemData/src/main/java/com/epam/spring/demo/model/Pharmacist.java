package com.epam.spring.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "pharmacists")
public class Pharmacist extends Person {
    private String schedule;


    @Column(name = "schedule")
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
}
