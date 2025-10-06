package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.dto.EmployeeMapper;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public EmployeeDTO getEmployeeById(Long id){
        Optional<Employee> foundEmployee = employeeRepo.findById(id);
        if (!foundEmployee.isPresent()){
            throw new RuntimeException("Employee could not be found with ID: " + id);
        }
        return EmployeeMapper.toDto(foundEmployee.get());
    }

    public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
        System.out.println("Empl service recieved request");
        // Mapper turns DTO to entity, repo saves that entity and returns it, then mapper turns it to a DTO
        return EmployeeMapper.toDto(employeeRepo.save(EmployeeMapper.toEntity(employeeDTO)));
    }
}
