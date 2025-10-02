package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.BookingDTO;
import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    ResponseEntity<List<BookingDTO>> getAllBookings(){
        List<BookingDTO> bookingList = bookingService.getAllBookings();

        return ResponseEntity.ok(bookingList);
    }

    @GetMapping("/{id}")
    ResponseEntity<BookingDTO> getBookingByID(@PathVariable Long id){
        return ResponseEntity.ok(bookingService.getBookingByID(id));
    }
}
