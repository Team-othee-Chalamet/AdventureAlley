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
}
