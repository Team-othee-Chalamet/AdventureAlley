package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Employee;

public class EmployeeMapper {

    static public EmployeeDTO toDto(Employee employee){
        return new EmployeeDTO(employee.getName(),
                employee.getSurname(),
                employee.getStaffId(),
                employee.getPassword());
    }

    static public Employee toEntity(EmployeeDTO employeeDTO){
        return new Employee(employeeDTO.name(),
                employeeDTO.surname(),
                employeeDTO.staffId(),
                employeeDTO.password());
    }
}
