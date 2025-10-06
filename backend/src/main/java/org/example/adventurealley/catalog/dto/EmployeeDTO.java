package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Employee;

public record EmployeeDTO(String name,
                          String lastName,
                          String staffId,
                          String password) {
}
