package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.EmployeeMapper;
import org.example.adventurealley.catalog.dto.LoginRequestDTO;
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

    public EmployeeDTO authenticate(LoginRequestDTO loginRequestDTO) {
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

        // If they match, return the found employee as a DTO
        return EmployeeMapper.toDto(foundEmployee);
    }

    // Optional helper to register new employees
    /*
    public Employee register(String username, String password) {
        Employee e = new Employee();
        e.setUsername(username);
        e.setPassword(SimpleHasher.hash(password));
        return employeeRepository.save(e);
    }
 */
}
