package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.BookingDTO;
import org.example.adventurealley.catalog.dto.BookingMapper;
import org.example.adventurealley.catalog.model.Booking;
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
            throw new RuntimeException();
        }
        return BookingMapper.toDto(foundBooking.get());
    }
}
