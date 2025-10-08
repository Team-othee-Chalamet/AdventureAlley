package org.example.adventurealley;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void testUpdateEmployeeIsPresent() {
        //Arrange
        Long id = 1L;
        Employee existingEmployee = new Employee("OldName", "OldSurname", "OldStaffId", "OldPassword");
        EmployeeDTO newDataEmployeeDTO = new EmployeeDTO("NewName", "NewSurname", "NewStaffId", "NewPassword");
        when(employeeRepo.findById(id)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepo.save(any(Employee.class)))
                /* When you ask mocks to perform methods, it is recorded as an invocation, in this case
                employeeRepo.save() is the invocation, invocation.getArgument(0) returns the argument passed to
                employeeRepo.save([argument]) - in this case the Employee we are trying to save */
                .thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(id, newDataEmployeeDTO);

        //Assert
        assertEquals(newDataEmployeeDTO, updatedEmployeeDTO);
    }
}
