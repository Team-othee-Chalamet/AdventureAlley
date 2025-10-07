package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;

import java.time.LocalDateTime;

public class TokenService {
    public static String generateToken(EmployeeDTO employeeDTO) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusHours(1);

        return "FAKE-TOKEN-FOR-"+employeeDTO.staffId()+" - Expires: "+expiration;
    }
}
