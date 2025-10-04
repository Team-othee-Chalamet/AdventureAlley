package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.time.LocalDate;

@Entity
public class Session extends BaseEntity {

    // Depending on timeslot logic, localDate might be redundant
    LocalDate date;
    String timeslot;
    String sessionActivity;
    @ManyToOne //Booking should contain (mappedBy = 'Session')
    Booking booking;

    public Session() {}

    public Session (LocalDate date, String timeslot, String sessionActivity, Booking booking) {
        this.date = date;
        this.timeslot = timeslot;
        this.sessionActivity = sessionActivity;
        this.booking = booking;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate startDate) {
        this.date = startDate;
    }

    public String getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(String timeslot) {
        this.timeslot = timeslot;
    }

    public String getSessionActivity() {
        return sessionActivity;
    }

    public void setSessionActivity(String sessionActivity) {
        this.sessionActivity = sessionActivity;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }
}
