package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Addon;
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

        /*
        List<AddonDTO> addonDTOs = new ArrayList<>();
        for (Addon addon : booking.getAddOns()) {
            addonDTOs.add(toDto(addon));
        }
        */
        // addonDTOs skal tilføjes igen
        return new BookingDTO(booking.getId(), booking.getPersonName(), booking.getPersonEmail(), booking.getPersonPhoneNr(), sessionDTOs);
    }

    static public Booking toEntity(BookingDTO bookingDTO){
        Booking booking = new Booking();
        booking.setId(bookingDTO.id());
        booking.setPersonName(bookingDTO.bookingName());
        booking.setPersonEmail(bookingDTO.bookingEmail());
        booking.setPersonPhoneNr(bookingDTO.bookingPhoneNr());

        for(SessionDTO sessionDTO: bookingDTO.sessionDtos()){
            booking.addSession(SessionMapper.toEntity(sessionDTO, booking));
        }

        /*
        for(AddonDTO addonDTO : bookingDTO.addonDTOs()){
            booking.addAddOn(toEntity(addonDTO));
        }
        */

        return booking;
    }

    static public AddonDTO toDto(Addon addon) {
        return new AddonDTO(addon.getId(), addon.getBooking(), addon.getProduct(), addon.getQuantity());
    }

    static public Addon toEntity(AddonDTO addonDTO) {
        Addon addon = new Addon();
        addon.setBooking(addonDTO.booking());
        addon.setProduct(addonDTO.product());
        addon.setQuantity(addonDTO.quantity());
        return addon;
    }
}
