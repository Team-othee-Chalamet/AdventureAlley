package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.AddonDTO;
import org.example.adventurealley.catalog.dto.BookingDTO;
import org.example.adventurealley.catalog.dto.BookingMapper;
import org.example.adventurealley.catalog.exceptions.BookingNotFoundException;
import org.example.adventurealley.catalog.model.Addon;
import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.repository.BookingRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
    BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<BookingDTO> getAllBookings(){
        List<Booking> foundBookings = bookingRepo.findAll();

        List<BookingDTO> returnBookings = new ArrayList<>();
        for (Booking booking: foundBookings){
            returnBookings.add(BookingMapper.toDto(booking));
        }
        return returnBookings;
    }

    public BookingDTO getBookingByID(Long id){
        Optional<Booking> foundBooking = bookingRepo.findById(id);
        if (!foundBooking.isPresent()){
            throw new BookingNotFoundException("Booking could not be found with id: " + id);
        }
        return BookingMapper.toDto(foundBooking.get());
    }

    public BookingDTO saveBooking(BookingDTO bookingDTO){
        //Converts to Entity, saves entity, returns DTO
        return BookingMapper.toDto(bookingRepo.save(BookingMapper.toEntity(bookingDTO)));
    }

    public BookingDTO updateBooking(Long id, BookingDTO bookingDTO){
        Optional<Booking> optionalBooking = bookingRepo.findById(id);
        if (!optionalBooking.isPresent()){
            throw new BookingNotFoundException("Booking could not be foind with id: " + id);
        }
        Booking foundBooking = optionalBooking.get();
        Booking newInfo = BookingMapper.toEntity(bookingDTO);

        foundBooking.setPersonName(newInfo.getPersonName());
        foundBooking.setPersonEmail(newInfo.getPersonEmail());
        foundBooking.setPersonPhoneNr(newInfo.getPersonPhoneNr());
        foundBooking.removeAllSessions();
        for (Session s: newInfo.getSessions()){
            foundBooking.addSession(s);
        }
        /*
        foundBooking.clearAddOns();
        for (Addon a: newInfo.getAddOns()){
            foundBooking.addAddOn(a);
        }
        */
        return BookingMapper.toDto(bookingRepo.save(foundBooking));
    }

    public void deleteBooking(Long id){
        Optional<Booking> optionalBooking = bookingRepo.findById(id);
        if (!optionalBooking.isPresent()){
            throw new BookingNotFoundException("Booking could not be found with id: " + id);
        }
        bookingRepo.delete(optionalBooking.get());
    }

    public BookingDTO addAddon(Long id, AddonDTO addonDTO){
        Optional<Booking> booking = bookingRepo.findById(id);
        if (booking.isEmpty()) {
            throw new BookingNotFoundException("Booking could not be found with id: " + id);
        }
        Booking existingBooking = booking.get();
        Addon addon = BookingMapper.toEntity(addonDTO);
        existingBooking.addAddOn(addon);
        return BookingMapper.toDto(bookingRepo.save(existingBooking));
    }

    public BookingDTO removeAddon(Long id, Long addonId){
        Optional<Booking> booking = bookingRepo.findById(id);
        if (booking.isEmpty()) {
            throw new BookingNotFoundException("Booking could not be found with id: " + id);
        }
        Booking existingBooking = booking.get();
        Addon addonToRemove = null;

        for (Addon addon : existingBooking.getAddOns()){
            if (addon.getId() == addonId){
                addonToRemove = addon;
                break;
            }
        }
        if (addonToRemove == null){
            throw new RuntimeException("Addon with id: " + addonId + " not found");
        }
        existingBooking.removeAddOn(addonToRemove);
        return BookingMapper.toDto(bookingRepo.save(existingBooking));
    }
}
