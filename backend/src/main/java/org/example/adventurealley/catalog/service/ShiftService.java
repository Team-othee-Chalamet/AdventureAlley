package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.ShiftDTO;
import org.example.adventurealley.catalog.dto.ShiftMapper;
import org.example.adventurealley.catalog.model.Shift;
import org.example.adventurealley.catalog.repository.ShiftRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftService {

    ShiftRepo shiftRepo;

    public ShiftService(ShiftRepo shiftRepo) {
        this.shiftRepo = shiftRepo;
    }
    public List<ShiftDTO> getAllShifts(){
        List<Shift> shiftSession = shiftRepo.findAll();
        List<ShiftDTO> shiftsDTOs = new ArrayList<>();

        for (Shift shift : shiftSession){
            shiftsDTOs.add(ShiftMapper.toDTO(shift));
        }

        return shiftsDTOs;
    }

}
