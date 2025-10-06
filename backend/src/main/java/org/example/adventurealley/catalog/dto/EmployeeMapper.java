package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Employee;
import org.springframework.context.support.BeanDefinitionDsl;

public class EmployeeMapper {

    static public EmployeeDTO toDto(Employee employee){
        return new EmployeeDTO(employee.getName(), employee.getLastName(), employee.getStaffId(), employee.getPassword());
    }
}
