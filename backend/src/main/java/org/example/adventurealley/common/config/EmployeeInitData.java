package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.utility.Hasher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EmployeeInitData implements CommandLineRunner {
    EmployeeRepo employeeRepo;

    public EmployeeInitData(EmployeeRepo employeeRepo){
        this.employeeRepo = employeeRepo;
    }

    @Override
    public void run(String... arg) {
        for (int i = 0; i < 10; i++) {
            String password = Hasher.encrypt("password"+i);
            Employee employee = new Employee("Name" +i, "Surname" +i, "staffId" +i, password);
            employeeRepo.save(employee);
        }
    }
}
