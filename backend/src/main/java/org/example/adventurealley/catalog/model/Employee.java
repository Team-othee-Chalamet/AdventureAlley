package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import org.example.adventurealley.common.baseClasses.BaseEntity;

@Entity
public class Employee extends BaseEntity {

    private String name;
    private String surname;
    private String staffId;
    private String password;

    public Employee(){};

    // Role as enum was causing a headache when making a DTO, so will come back

    public Employee(String name, String surname, String staffId, String password) {
        this.name = name;
        this.surname = surname;
        this.staffId = staffId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
