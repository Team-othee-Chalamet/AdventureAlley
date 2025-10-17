package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.model.activities.ActivityGoKart;
import org.example.adventurealley.catalog.model.activities.ActivityMiniGolf;
import org.example.adventurealley.catalog.model.activities.ActivityType;
import org.example.adventurealley.catalog.repository.ActivityRepo;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.repository.ShiftRepo;
import org.example.adventurealley.catalog.service.EmployeeService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ShiftInitData implements CommandLineRunner {

    private final EmployeeRepo employeeRepo;
    private final ShiftRepo shiftRepo;
    EmployeeService employeeService;


    public ShiftInitData(EmployeeRepo employeeRepo, ShiftRepo shiftRepo) {
        this.employeeRepo = employeeRepo;
        this.shiftRepo = shiftRepo;
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting CommandLineRunner to initialize Shift data");

        if (shiftRepo.count() < 2) { // already have data
            employeeRepo.save(new Employee("Ole", "Ole", "Ole", "Ole"));
            employeeRepo.save(new Employee("Lars", "Lars", "Lars", "Lars"));
        }
        var list = employeeRepo.findAll();
        var e1 = list.get(0);
        var e2 = list.get(1);

        LocalDate base = LocalDate.of(2025,10,21);



        // helper to build and save a shift with setters (works even if your constructor is empty)
        saveShift(base,        LocalTime.of(9, 0),  LocalTime.of(13, 0), e1);
        saveShift(base,        LocalTime.of(13, 0), LocalTime.of(17, 0), e2);
        saveShift(base.plusDays(1), LocalTime.of(10, 0), LocalTime.of(14, 0), e1);
        saveShift(base.plusDays(2), LocalTime.of(12, 0), LocalTime.of(18, 0), e2);
        saveShift(base.plusDays(3), LocalTime.of(8, 30), LocalTime.of(12, 30), e1);
        saveShift(base.plusDays(4), LocalTime.of(15, 0), LocalTime.of(20, 0), e2);

        System.out.println("Seeded dummy shifts");
    }

    private void saveShift(LocalDate date, LocalTime start, LocalTime end, Employee emp) {
        Shift s = new Shift();
        s.setDate(date);
        s.setStartTime(start);
        s.setEndTime(end);
        s.setEmployee(emp);
        shiftRepo.save(s);
    }
}
