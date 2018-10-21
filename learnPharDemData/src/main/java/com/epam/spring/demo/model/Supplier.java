package com.epam.spring.demo.model;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "suppliers")
public class Supplier extends Company {


    private Supplier contactPerson;

    @OneToOne
    @JoinColumn(name="contactPerson_id")
    public Supplier getContactPerson() {        return contactPerson;    }
    public void setContactPerson(Supplier contactPerson) {        this.contactPerson = contactPerson;    }
}
