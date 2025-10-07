package org.example.adventurealley.catalog.model;


import jakarta.persistence.Entity;
import org.example.adventurealley.common.baseClasses.BaseEntity;

@Entity
public class Employee extends BaseEntity {
    enum Role{ADMIN, REGULAR}
    private String name;
    private String lastName;
    private String staffId;
    private String password;
    private Role role;

    public Employee(){};

    public Employee(String name, String lastName, String staffId, String password, String role) {
        this.name = name;
        this.lastName = lastName;
        this.staffId = staffId;
        this.password = password;
        this.role = Role.valueOf(role.toUpperCase());
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
