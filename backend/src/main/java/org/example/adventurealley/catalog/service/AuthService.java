package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.EmployeeMapper;
import org.example.adventurealley.catalog.dto.LoginRequestDTO;
import org.example.adventurealley.catalog.dto.LoginResponseDTO;
import org.example.adventurealley.catalog.exceptions.InvalidTokenException;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.utility.Hasher;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
public class AuthService {
    EmployeeRepo employeeRepo;

    public AuthService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    public LoginResponseDTO authenticateLogin(LoginRequestDTO loginRequestDTO) {
        System.out.println("AuthService: authenticateLogin");
        String staffId = loginRequestDTO.staffId();
        String password = loginRequestDTO.password();

        // Check if there is an employee with that staffId
        Optional<Employee> optionalEmployee = employeeRepo.findByStaffId(staffId);
        if(!optionalEmployee.isPresent()) {
            throw new RuntimeException("No employee found with ID: "+staffId);
        }

        // If that staffId exists, check if the password matches
        Employee foundEmployee = optionalEmployee.get();
        if(!Hasher.encrypt(password).equals(foundEmployee.getPassword())) {
            throw new RuntimeException("Staff ID and password does not match");
        }
        //Turn employee into a DTO
        EmployeeDTO employeeDTO = EmployeeMapper.toDto(foundEmployee);

        // Generate a (simple "fake") token
        String returnToken = TokenService.generateToken(employeeDTO);

        // Create the response and return it
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(returnToken, employeeDTO);

        return loginResponseDTO;
    }

    public EmployeeDTO authenticateToken(String authHeader) {
        System.out.println("Trying to authenticate: "+authHeader);
        // Check if there is a header
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            throw new InvalidTokenException("Invalid or missing token");
        }

        // Remove redundant info
        String token = authHeader.replace("Bearer FAKE-TOKEN-FOR-", "");

        try {
            // Split string into staffId and time for expiration (as instant)
            String[] parts = token.split(" - Expires: ");
            String staffId = parts[0];
            Instant expiry = Instant.parse(parts[1]);

            // Check token is not expired
            if (Instant.now().isAfter(expiry)) {
                throw new InvalidTokenException("Token expired");
            }

            // Check if staffId exists
            Optional<Employee> optionalEmployee = employeeRepo.findByStaffId(staffId);
            if (!optionalEmployee.isPresent()){
                throw new InvalidTokenException("No employee with ID "+ staffId);
            }

            EmployeeDTO foundEmployee = EmployeeMapper.toDto(optionalEmployee.get());

            return foundEmployee;
        } catch (Exception e) {
            throw new InvalidTokenException("Invalid token");
        }
    }

    public boolean authenticateTokenBoolean(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer")) {
            return false;
        }

        // Remove redundant info
        String token = authHeader.replace("Bearer FAKE-TOKEN-FOR-", "");


            // Split string into staffId and time for expiration (as instant)
            String[] parts = token.split(" - Expires: ");
            String staffId = parts[0];
            Instant expiry = Instant.parse(parts[1]);

            // Check token is not expired
            if (Instant.now().isAfter(expiry)) {
                return false;
            }

            // Check if staffId exists
            Optional<Employee> optionalEmployee = employeeRepo.findByStaffId(staffId);
            if (!optionalEmployee.isPresent()){
                return false;
            }

            return true;
    }
}
