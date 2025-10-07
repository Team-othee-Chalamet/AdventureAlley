package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.BookingDTO;
import org.example.adventurealley.catalog.dto.BookingMapper;
import org.example.adventurealley.catalog.exceptions.BookingNotFoundException;
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
            throw new BookingNotFoundException("Booking could not be foind with id: " + id);
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


        return BookingMapper.toDto(bookingRepo.save(foundBooking));
    }

    public void deleteBooking(Long id){
        Optional<Booking> optionalBooking = bookingRepo.findById(id);
        if (!optionalBooking.isPresent()){
            throw new BookingNotFoundException("Booking could not be foind with id: " + id);
        }
        bookingRepo.delete(optionalBooking.get());
    }
}
