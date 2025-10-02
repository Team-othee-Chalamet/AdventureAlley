package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.repository.BookingRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitData implements CommandLineRunner {
    BookingRepo bookingRepo;

    public InitData(BookingRepo bookingRepo) {
        this.bookingRepo = bookingRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Starting commandLine Runner to initialize Data");

        for (int i = 0; i < 10; i++) {
            Booking booking = new Booking(""+i, ""+i, ""+i);
            bookingRepo.save(booking);
        }
    }
}
