package org.example.adventurealley.catalog.model;

import jakarta.persistence.*;
import org.example.adventurealley.catalog.model.activities.ActivityType;
import org.example.adventurealley.common.baseClasses.BaseEntity;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "shifts")
public class Shift extends BaseEntity {

    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private ActivityType activityType;

    @ManyToOne
    @JoinColumn(name = "empoyeeId")
    private Employee employee;

    public Shift() {
    }

    public Shift(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee, ActivityType activityType) {
        super();
    }

    public Shift(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.employee = employee;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setActivityType(ActivityType activity) {
        this.activityType = activity;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "employee=" + employee +
                ", activityType=" + activityType +
                ", endTime=" + endTime +
                ", startTime=" + startTime +
                ", date=" + date +
                '}';
    }
}
