package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.repository.ActivityRepo;
import org.example.adventurealley.catalog.repository.EmployeeRepo;
import org.example.adventurealley.catalog.service.ShiftService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    ActivityRepo activityRepo;
    ShiftService shiftService;

    public ShiftController(ShiftService shiftService, EmployeeRepo employeeRepo, ActivityRepo activityRepo) {
        this.shiftService = shiftService;
        this.employeeRepo = employeeRepo;
        this.activityRepo = activityRepo;
    }

    @GetMapping
    ResponseEntity<List<ShiftDTO>> getAllShifts() {
        List<ShiftDTO> shiftDtoList = shiftService.getAllShifts();
        return ResponseEntity.ok().body(shiftDtoList);
    }

    @PostMapping
    public ShiftDTO createShift(@RequestParam LocalDate date,
                                @RequestParam LocalTime startTime,
                                @RequestParam LocalTime endTime,
                                @RequestParam Long  employeeId,
                                @RequestParam Long activityId){
        Employee emp = (employeeId!=null) ? employeeRepo.findById(employeeId).orElse(null) : null; // noget midlertidigt chat kode som sørger for employee or activity kan være null
        Activity act = (activityId!=null) ? activityRepo.findById(activityId).orElse(null) : null;
        System.out.println("createShift() i shiftController.java er kørt");
        return shiftService.createShift(date, startTime, endTime, emp, act);
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
