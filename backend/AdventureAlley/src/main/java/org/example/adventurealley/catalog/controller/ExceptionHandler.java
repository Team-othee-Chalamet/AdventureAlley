package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.exceptions.BookingNotFoundException;
import org.example.adventurealley.catalog.model.Booking;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    record ErrorResponse(int status, String message){}

    @org.springframework.web.bind.annotation.ExceptionHandler(BookingNotFoundException.class)
    ResponseEntity<ErrorResponse> bookingNotFound(BookingNotFoundException ex){
        System.out.println("DER ER EN FEJL");
        ErrorResponse errorResponse = new ErrorResponse(404, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }
}
