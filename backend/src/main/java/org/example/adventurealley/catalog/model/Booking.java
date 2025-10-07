package org.example.adventurealley.catalog.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking extends BaseEntity {

    String personName;
    String personEmail;
    String personPhoneNr;

    @OneToMany (mappedBy = "booking", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Addon> addOns = new ArrayList<>();

    public Booking(){}

    public Booking(String personName, String personEmail, String personPhoneNr) {
        this.personName = personName;
        this.personEmail = personEmail;
        this.personPhoneNr = personPhoneNr;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPersonEmail() {
        return personEmail;
    }

    public void setPersonEmail(String personEmail) {
        this.personEmail = personEmail;
    }

    public String getPersonPhoneNr() {
        return personPhoneNr;
    }

    public void setPersonPhoneNr(String personPhoneNr) {
        this.personPhoneNr = personPhoneNr;
    }
}
