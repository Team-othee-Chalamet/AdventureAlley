package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.activities.ActivityType;

import java.time.LocalDate;
import java.time.LocalTime;

public record ShiftDTO(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee, Activity activity) {
}
