package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Booking;

import java.time.LocalDate;

public record SessionDTO(LocalDate date, String timeslot, String sessionActivity, Booking booking) {
}
