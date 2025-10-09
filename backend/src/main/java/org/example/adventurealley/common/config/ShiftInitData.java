package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.repository.ActivityRepo;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.repository.ShiftRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
public class ShiftInitData implements CommandLineRunner {

    private final EmployeeRepo employeeRepo;
    private final ActivityRepo activityRepo;
    private final ShiftRepo shiftRepo;

    public ShiftInitData(EmployeeRepo employeeRepo, ActivityRepo activityRepo, ShiftRepo shiftRepo) {
        this.employeeRepo = employeeRepo;
        this.activityRepo = activityRepo;
        this.shiftRepo = shiftRepo;
    }

    @Override
    public void run(String... args) {
        System.out.println("Starting CommandLineRunner to initialize Shift data");

        if (shiftRepo.count() > 0) return; // already have data

        // Reuse existing employees/activities if they exist; otherwise create blank ones.
        List<Employee> employees = employeeRepo.findAll();
        Employee e1 = employees.size() > 0 ? employees.get(0) : employeeRepo.save(new Employee());
        Employee e2 = employees.size() > 1 ? employees.get(1) : employeeRepo.save(new Employee());

        List<Activity> activities = activityRepo.findAll();
        Activity a1 = activities.size() > 0 ? activities.get(0) : activityRepo.save(new Activity());
        Activity a2 = activities.size() > 1 ? activities.get(1) : activityRepo.save(new Activity());

        LocalDate base = LocalDate.now();

        // helper to build and save a shift with setters (works even if your constructor is empty)
        saveShift(base,        LocalTime.of(9, 0),  LocalTime.of(13, 0), e1, a1);
        saveShift(base,        LocalTime.of(13, 0), LocalTime.of(17, 0), e2, a1);
        saveShift(base.plusDays(1), LocalTime.of(10, 0), LocalTime.of(14, 0), e1, a2);
        saveShift(base.plusDays(2), LocalTime.of(12, 0), LocalTime.of(18, 0), e2, a2);
        saveShift(base.plusDays(3), LocalTime.of(8, 30), LocalTime.of(12, 30), e1, a1);
        saveShift(base.plusDays(4), LocalTime.of(15, 0), LocalTime.of(20, 0), e2, a2);

        System.out.println("Seeded dummy shifts");
    }

    private void saveShift(LocalDate date, LocalTime start, LocalTime end, Employee emp, Activity act) {
        Shift s = new Shift();
        s.setDate(date);
        s.setStartTime(start);
        s.setEndTime(end);
        s.setEmployee(emp);
        s.setActivity(act);
        shiftRepo.save(s);
    }
}
