package org.example.adventurealley.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import org.example.adventurealley.catalog.dto.SessionDTO;
import jakarta.persistence.OneToMany;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Booking extends BaseEntity {

    String personName;
    String personEmail;
    String personPhoneNr;

    @OneToMany (mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Addon> addOns = new ArrayList<>();

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public List<Addon> getAddOns() {
        return addOns;
    }

    public void addAddOn(Addon addon) {
        addOns.add(addon);
        addon.setBooking(this);
    }

    public void removeAddOn(Addon addon) {
        addOns.remove(addon);
        addon.setBooking(null);
    }

    public void clearAddOns() {
        for (Addon addon : new ArrayList<>(addOns)) {
            removeAddOn(addon);
        }
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void addSession(Session session) {
        sessions.add(session);
        session.setBooking(this);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
        session.setBooking(null);
    }

    public void removeAllSessions() {
        for (Session session : sessions) {
            session.setBooking(null);
        }
        sessions.clear();
    }
}
