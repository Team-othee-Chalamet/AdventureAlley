package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.LoginRequestDTO;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        try {
            EmployeeDTO employeeDTO = authService.authenticate(loginRequestDTO);
            return ResponseEntity.ok(Map.of(
                    "token", "FAKE-TOKEN-FOR-" +employeeDTO.staffId(),
                    "employee", employeeDTO
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
