package org.example.adventurealley.catalog.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.adventurealley.catalog.dto.EmployeeDTO;
import org.example.adventurealley.catalog.exceptions.InvalidTokenException;
import org.example.adventurealley.catalog.service.AuthService;
import org.example.adventurealley.catalog.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    EmployeeService employeeService;
    AuthService authService;

    public EmployeeController(EmployeeService employeeService, AuthService authService){
        this.employeeService = employeeService;
        this.authService = authService;
    }

    @ModelAttribute
    //Before any request in the EmployeeController, it checks the token and sets currentUser based on the token
    public void validateToken(@RequestHeader("Authorization") String authHeader, HttpServletRequest httpRequest) {
        EmployeeDTO requestingEmployee = authService.authenticateToken(authHeader);
        httpRequest.setAttribute("currentUser", requestingEmployee);
    }

    @GetMapping
     ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employeeList = employeeService.getAllEmployees();
        return ResponseEntity.ok(employeeList);
    }

    @GetMapping("/{id}")
        ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO){
        System.out.println("Empl Controller recieved request");
        return ResponseEntity.ok(employeeService.saveEmployee(employeeDTO));
    }

    @PutMapping("/{id}")
    ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeDTO));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<EmployeeDTO> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/currentUser")
    ResponseEntity<EmployeeDTO> getCurrentUser(@RequestAttribute("currentUser") EmployeeDTO currentUser) {
        return ResponseEntity.ok(currentUser);
    }
}
