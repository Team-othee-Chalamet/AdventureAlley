package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Booking;

public class BookingMapper {

    static public BookingDTO toDto(Booking booking){
        return new BookingDTO(booking.getPersonName(), booking.getPersonEmail(), booking.getPersonPhoneNr());
    }

    static public Booking toEntity(BookingDTO bookingDTO){
        return new Booking(bookingDTO.bookingName(), bookingDTO.bookingEmail(), bookingDTO.bookingPhoneNr());
    }
}
