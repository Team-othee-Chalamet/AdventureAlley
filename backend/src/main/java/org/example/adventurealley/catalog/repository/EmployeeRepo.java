package org.example.adventurealley.catalog.repository;

import org.example.adventurealley.catalog.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepo extends JpaRepository<Employee, Long> {
    Optional<Employee> findByStaffId(String staffId);
}
