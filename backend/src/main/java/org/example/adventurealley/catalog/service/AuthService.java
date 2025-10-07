package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.EmployeeMapper;
import org.example.adventurealley.catalog.dto.LoginRequestDTO;
import org.example.adventurealley.catalog.dto.LoginResponseDTO;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.utility.Hasher;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    EmployeeRepo employeeRepo;

    public AuthService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    public LoginResponseDTO authenticate(LoginRequestDTO loginRequestDTO) {
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

}
