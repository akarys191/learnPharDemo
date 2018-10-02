package com.epam.spring.demo.model;

public class Pharmacist extends Person {
    private Schedule schedule;


    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
}
