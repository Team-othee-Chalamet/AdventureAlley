package org.example.adventurealley.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking extends BaseEntity {

    String personName;
    String personEmail;
    String personPhoneNr;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    List<Session> sessions = new ArrayList<>();

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

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
        session.setBooking(this);
    }
}
