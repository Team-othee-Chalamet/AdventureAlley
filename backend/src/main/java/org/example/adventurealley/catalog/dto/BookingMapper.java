package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;

import java.util.ArrayList;
import java.util.List;

public class BookingMapper {

    static public BookingDTO toDto(Booking booking){
        List<SessionDTO> sessionDTOs = new ArrayList<>();
        for (Session s: booking.getSessions()){
            sessionDTOs.add(SessionMapper.toDTO(s));
        }


        return new BookingDTO(booking.getPersonName(), booking.getPersonEmail(), booking.getPersonPhoneNr(), sessionDTOs);
    }

    static public Booking toEntity(BookingDTO bookingDTO){
        Booking booking = new Booking();
        booking.setPersonName(bookingDTO.bookingName());
        booking.setPersonEmail(bookingDTO.bookingEmail());
        booking.setPersonPhoneNr(bookingDTO.bookingPhoneNr());

        for(SessionDTO sessionDTO: bookingDTO.sessionDtos()){
            booking.addSession(SessionMapper.toEntity(sessionDTO, booking));
        }


        return booking;
    }
}
