package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Employee;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/shifts")
public class ShiftController {

    ShiftService shiftService;

    @GetMapping
    ResponseEntity<List<ShiftDTO>> getAllShifts() {
        List<ShiftDTO> shiftDtoList = shiftService.getAllShifts();
        return ResponseEntity.ok().body(shiftDtoList);
    }

    @PostMapping
    public ShiftDTO createShift(LocalDate date, LocalTime startTime, LocalTime endTime, Employee employee, Activity activity){
        return shiftService.createShift(date, startTime, endTime, employee, activity);
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
