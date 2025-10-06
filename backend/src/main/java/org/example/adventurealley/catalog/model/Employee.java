package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import org.example.adventurealley.common.baseClasses.BaseEntity;

@Entity
public class Employee extends BaseEntity {

    private String name;
    private String lastName;
    private String staffId;
    private String password;

    public Employee(){};

    // Role as enum was causing a headache when making a DTO, so will come back

    public Employee(String name, String lastName, String staffId, String password) {
        this.name = name;
        this.lastName = lastName;
        this.staffId = staffId;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
