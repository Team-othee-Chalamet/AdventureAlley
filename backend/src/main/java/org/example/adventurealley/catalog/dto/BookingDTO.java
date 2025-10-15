package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Session;

import java.util.List;

public record BookingDTO(Long id,
                         String bookingName,
                         String bookingEmail,
                         String bookingPhoneNr,
                         List<SessionDTO> sessionDtos) {}
