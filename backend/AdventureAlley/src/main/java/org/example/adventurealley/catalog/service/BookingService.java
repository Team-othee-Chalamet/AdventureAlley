package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.repository.BookingRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {
    BookingRepo bookingRepo;

    public BookingService(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    public List<Booking> getAllBookings(){
        return bookingRepo.findAll();
    }
}
