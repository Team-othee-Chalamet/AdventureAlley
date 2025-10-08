package org.example.adventurealley;

import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepo employeeRepo;

    @InjectMocks
    private EmployeeService employeeService;

    private Long id;
    private Employee existingEmployee;
    private EmployeeDTO newDataEmployeeDTO;

    @BeforeEach
    void setup () {
        id = 1l;
        existingEmployee = new Employee("OldName", "OldSurname", "OldStaffId", "OldPassword");
        newDataEmployeeDTO = new EmployeeDTO("NewName", "NewSurname", "NewStaffId", "NewPassword");
    }

    @Test
    void testUpdateEmployeeIsPresent() {
        //Arrange
        when(employeeRepo.findById(id)).thenReturn(Optional.of(existingEmployee));
        /* When you ask mocks to perform methods, it is recorded as an invocation, in this case
                employeeRepo.save() is the invocation, invocation.getArgument(0) returns the argument passed to
                employeeRepo.save([argument]) - in this case the Employee we are trying to save */
        when(employeeRepo.save(any(Employee.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        //Act
        EmployeeDTO updatedEmployeeDTO = employeeService.updateEmployee(id, newDataEmployeeDTO);

        //Assert
        assertEquals(newDataEmployeeDTO, updatedEmployeeDTO);
    }

    @Test
    void testUpdateEmployeeIsNotPresent() {
        // Arrange
        when(employeeRepo.findById(id)).thenReturn(Optional.empty());

        //Act
        //I expect employeeService... to throw a RuntimeException, and I store that as a variable
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () ->
                employeeService.updateEmployee(id, newDataEmployeeDTO));

        //Assert
        assertEquals("No employee found with ID: "+id, runtimeException.getMessage());
    }
}
