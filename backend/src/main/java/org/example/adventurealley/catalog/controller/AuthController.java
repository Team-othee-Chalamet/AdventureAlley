package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.LoginRequestDTO;
import org.example.adventurealley.catalog.dto.LoginResponseDTO;
import org.example.adventurealley.catalog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
            // Service checks if credentials match
            LoginResponseDTO loginResponseDTO = authService.authenticateLogin(loginRequestDTO);

            EmployeeDTO employeeDTO = loginResponseDTO.employeeDTO();
            String token = loginResponseDTO.token();

            // Returns ResponseEntity with mapped key / value pairs for token and employee
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "employee", employeeDTO
            ));
            // If credentials don't match, returns an error message
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
