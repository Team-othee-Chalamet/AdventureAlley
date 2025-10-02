package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {
    BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    ResponseEntity<List<Booking>> getAllBookings(){
        List<Booking> bookingList = bookingService.getAllBookings();


        return ResponseEntity.ok(bookingList);

    }
}
