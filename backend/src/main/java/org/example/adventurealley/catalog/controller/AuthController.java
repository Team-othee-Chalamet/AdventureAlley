package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.LoginRequestDTO;
import org.example.adventurealley.catalog.dto.LoginResponseDTO;
import org.example.adventurealley.catalog.exceptions.InvalidTokenException;
import org.example.adventurealley.catalog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

            // Returns ResponseEntity with mapped key / value pairs for token and employee
            return ResponseEntity.ok(Map.of(
                    "token", loginResponseDTO.token(),
                    "employee", loginResponseDTO.employeeDTO()
            ));
            // If credentials don't match, returns an error message
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    @PostMapping("/validateToken")
    public ResponseEntity<?> testToken(@RequestHeader("Authorization") String authHeader) {
        try {
            return ResponseEntity.ok(authService.authenticateToken(authHeader));
        } catch (InvalidTokenException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
