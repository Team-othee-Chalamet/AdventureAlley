package org.example.adventurealley.catalog.model;

import jakarta.persistence.*;
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

    @ManyToOne
    @JoinColumn(name = "empoyeeId")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "activityId")
    private Activity activity;

    public Shift() {
    }

    public Shift(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee, Activity activity) {
        super();
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

    public void setActivity(Activity activity) {
        this.activity = activity;
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

    public Activity getActivity() {
        return activity;
    }
}
