package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    void createShift(){
        Shift newShift = new Shift();
    }

    @GetMapping("/{id}")
    private Shift getShiftById(Long id){
        return shiftService.getShiftById(id);
    }

    @DeleteMapping("/{id}")
    private void deleteShiftById(Long id){
        shiftService.deleteShiftById(id);
    }



}
