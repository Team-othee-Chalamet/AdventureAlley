package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Session;

import java.util.List;

public record BookingDTO(String bookingName, String bookingEmail, String bookingPhoneNr, List<SessionDTO> sessionDtos, List<AddonDTO> addonDTOs) {}
