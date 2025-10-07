package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.service.ShiftService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
