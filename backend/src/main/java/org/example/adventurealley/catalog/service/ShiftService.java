package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.dto.ShiftMapper;
import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.repository.ShiftRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ShiftService {

    private final ShiftRepo shiftRepo;
    private final EmployeeRepo employeeRepo;

    public ShiftService(ShiftRepo shiftRepo, EmployeeRepo employeeRepo) {
        this.shiftRepo = shiftRepo;
        this.employeeRepo = employeeRepo;
    }
    public List<ShiftDTO> getAllShifts(){
        List<Shift> shiftSession = shiftRepo.findAll();
        List<ShiftDTO> shiftsDTOs = new ArrayList<>();

        for (Shift shift : shiftSession){
            shiftsDTOs.add(ShiftMapper.toDTO(shift));
        }

        return shiftsDTOs;
    }

    public Shift getShiftById(Long id) {

        return shiftRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Shift "+ id + "Not found"));
    }

    public void deleteShiftById(Long id) {
    shiftRepo.deleteById(id);
    }


    public void updateShiftEmployee(Long id, Long employeeId){

        Shift shift = shiftRepo.getById(id);
        Employee employee = employeeRepo.getById(employeeId);

        shift.setEmployee(employee);
        shiftRepo.save(shift);
        System.out.println("Employee updated");
    }

    public ShiftDTO createShift(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee, Activity activity) {
        Shift shift = new Shift(date, startTime, endTime, employee, activity);
        shiftRepo.save(shift);
        return new ShiftDTO(date, startTime, endTime, employee, activity);
    }
}
