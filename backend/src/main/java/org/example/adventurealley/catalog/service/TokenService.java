package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class TokenService {
    public static String generateToken(EmployeeDTO employeeDTO) {
        // Instant is an absolute measurement of time ignoring time-zones
        Instant now = Instant.now();
        Instant expiration = now.plus(1, ChronoUnit.HOURS);

        return "FAKE-TOKEN-FOR-"+employeeDTO.staffId()+" - Expires: "+expiration.toString();
    }

    public static String getStaffIdFromToken(String token) {
        token.replace("Bearer FAKE-TOKEN-FOR-", "");
        String[] parts = token.split(" - Expires: ");
        String staffId = parts[0];
        return staffId;
    }

    public Instant getExpirationFromToken(String token) {
        token.replace("Bearer FAKE-TOKEN-FOR-", "");
        String[] parts = token.split(" - Expires: ");
        Instant expiration = Instant.parse(parts[1]);
        return expiration;
    }
}
