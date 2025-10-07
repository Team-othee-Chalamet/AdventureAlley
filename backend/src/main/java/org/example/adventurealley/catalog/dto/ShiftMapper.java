package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Shift;

public class ShiftMapper {

    public static ShiftDTO toDTO(Shift shift){
        ShiftDTO shiftDTO = new ShiftDTO(shift.getDate(), shift.getStartTime(), shift.getEndTime(), shift.getEmployee(), shift.getActivity());
        return shiftDTO;
    }


}
