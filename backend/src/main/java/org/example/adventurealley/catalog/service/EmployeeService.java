package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.EmployeeMapper;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {
    EmployeeRepo employeeRepo;

    public EmployeeService(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    public List<EmployeeDTO> getAllEmployees(){
        List<Employee> foundEmployees = employeeRepo.findAll();

        List<EmployeeDTO> returnEmployees = new ArrayList<>();
        for (Employee employee: foundEmployees) {
            returnEmployees.add(EmployeeMapper.toDto(employee));
        }
        return returnEmployees;
    }
}
