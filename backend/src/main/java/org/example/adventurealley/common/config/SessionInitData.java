package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.repository.BookingRepo;
import org.example.adventurealley.catalog.repository.SessionRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SessionInitData implements CommandLineRunner {

    SessionRepo sessionRepo;
    BookingRepo bookingRepo;
    public SessionInitData(SessionRepo sessionRepo, BookingRepo bookingRepo) {
        this.sessionRepo = sessionRepo;
        this.bookingRepo = bookingRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        Booking booking = new Booking();
        bookingRepo.save(booking);

        for (int i = 0; i < 10; i++) {
            Session session = new Session(LocalDate.now(), "Timeslot" +i, "Activity" +i, booking);
            sessionRepo.save(session);
        }
    }
}