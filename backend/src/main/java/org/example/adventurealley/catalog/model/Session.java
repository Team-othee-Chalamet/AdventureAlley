package org.example.adventurealley.catalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import org.example.adventurealley.catalog.model.activities.ActivityFactory;
import org.example.adventurealley.catalog.model.activities.ActivityType;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Session extends BaseEntity implements Comparable<Session>{

    // Depending on timeslot logic, localDate might be redundant
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    double price;

    @Enumerated(EnumType.STRING)
    ActivityType activityType;

    @ManyToOne //Booking should contain (mappedBy = 'Session')
    Booking booking;
    Boolean bookingStatus = true;

    public Session() {}

    public Session (LocalDate date, LocalTime startTime, LocalTime endTime, Booking booking, ActivityType activityType) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.booking = booking;
        this.activityType = activityType;
        if(this.booking == null){
            this.bookingStatus = false;
        }
        this.price = this.calcPrice();
    }

    private double calcPrice(){
        double basePrice = ActivityFactory.getActivity(activityType).getPrice();
        double currentPrice = basePrice;

        if (this.date.getDayOfWeek() != DayOfWeek.SATURDAY && this.date.getDayOfWeek() != DayOfWeek.SUNDAY){
            currentPrice = currentPrice-(basePrice*0.20);
        }

        if (this.startTime.isBefore(LocalTime.of(12,0,0))) {
            currentPrice = currentPrice-(basePrice*0.20);
        }

        currentPrice = Math.round(currentPrice * 100) / 100;

        return currentPrice;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate startDate) {
        this.date = startDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Activity getActivity(){
        return ActivityFactory.getActivity(this.activityType);
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public boolean getBookingStatus(){
        return bookingStatus;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int compareTo(Session o) {
        int cmp = this.getDate().compareTo(o.getDate());

        if (cmp == 0){
            cmp = this.getStartTime().compareTo(o.getStartTime());
        }
        return cmp;
    }
}
