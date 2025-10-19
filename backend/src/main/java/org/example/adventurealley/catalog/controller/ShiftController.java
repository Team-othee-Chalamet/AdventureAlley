package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.model.activities.ActivityType;
import org.example.adventurealley.catalog.repository.ActivityRepo;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.service.ShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    private static final Logger log = LoggerFactory.getLogger(ShiftController.class);
    EmployeeRepo employeeRepo;
    ShiftService shiftService;

    public ShiftController(ShiftService shiftService, EmployeeRepo employeeRepo) {
        this.shiftService = shiftService;
        this.employeeRepo = employeeRepo;
    }

    @GetMapping
    ResponseEntity<List<ShiftDTO>> getAllShifts() {
        List<ShiftDTO> shiftDtoList = shiftService.getAllShifts();
        return ResponseEntity.ok().body(shiftDtoList);
    }

    @PostMapping
    public ShiftDTO createShift(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,

            @RequestParam("startTime")
            @DateTimeFormat(pattern = "HH:mm") LocalTime startTime,

            @RequestParam("endTime")
            @DateTimeFormat(pattern = "HH:mm") LocalTime endTime,

            @RequestParam("employeeId") String employeeId,
            @RequestParam("id") Long id
    ) {
        System.out.println("IN: date=" + date + ", start=" + startTime +
                ", end=" + endTime + ", empId=" + employeeId);
        Employee emp = employeeRepo.findByStaffId(employeeId).orElse(null);
        return shiftService.createShift(id, date, startTime, endTime, emp);
    }


    @GetMapping("/{id}")
    private Shift getShiftById(@PathVariable Long id){
        return shiftService.getShiftById(id);
    }
    @DeleteMapping("/{id}")
    private void deleteShiftById(@PathVariable Long id){
        shiftService.deleteShiftById(id);
    }
    @PutMapping("/{id}/employee")
    private void updateShiftEmplpoyee(@PathVariable Long shiftId,
                                      @RequestParam Long employeeId){
        shiftService.updateShiftEmployee(shiftId,employeeId);
    }
}
