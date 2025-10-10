package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.AddonDTO;
import org.example.adventurealley.catalog.dto.BookingDTO;
import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @PostMapping
    ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingDTO){
        System.out.println("Recieved a new booking");
        return ResponseEntity.ok(bookingService.saveBooking(bookingDTO));
    }

    @PutMapping("/{id}")
    ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDTO){
        return ResponseEntity.ok(bookingService.updateBooking(id, bookingDTO));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<BookingDTO> deleteBooking(@PathVariable Long id){
        bookingService.deleteBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/addons")
    public ResponseEntity<BookingDTO> addAddon(@PathVariable Long id, @RequestBody AddonDTO addonDTO){
        try {
            return ResponseEntity.ok(bookingService.addAddon(id, addonDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}/addons/{addonId}")
    public ResponseEntity<BookingDTO> removeAddon(@PathVariable Long id, @PathVariable Long addonId){
        return ResponseEntity.ok(bookingService.removeAddon(id, addonId));
    }

}
