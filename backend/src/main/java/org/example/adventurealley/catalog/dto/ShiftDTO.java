package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Employee;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShiftDTO(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee) {
}
