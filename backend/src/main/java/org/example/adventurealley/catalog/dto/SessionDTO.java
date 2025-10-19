package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.activities.ActivityType;

import java.time.LocalDate;
import java.time.LocalTime;

public record SessionDTO(LocalDate date,
                         LocalTime startTime,
                         LocalTime endTime,
                         ActivityType activityType,
                         double price,
                         boolean isPerPerson,
                         boolean bookingStatus) {
}
