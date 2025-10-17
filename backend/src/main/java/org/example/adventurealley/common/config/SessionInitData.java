package org.example.adventurealley.common.config;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.model.activities.ActivityType;
import org.example.adventurealley.catalog.repository.BookingRepo;
import org.example.adventurealley.catalog.repository.SessionRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

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
        Booking booking = new Booking("Mikkel", "MikkelMail", "30504870", 100, 5);
        bookingRepo.save(booking);

        Session session1 = new Session(LocalDate.now(), LocalTime.of(9,0,0), LocalTime.of(11,0,0), booking, ActivityType.Gokart);
        Session session2 = new Session(LocalDate.now(), LocalTime.of(13,0,0), LocalTime.of(16,0,0), booking, ActivityType.Paintball);
        Session session3 = new Session(LocalDate.now(), LocalTime.of(15,30,0), LocalTime.of(16,0,0), booking, ActivityType.Sumo);
        Session session4 = new Session(LocalDate.now(), LocalTime.of(15,0,0), LocalTime.of(17,0,0), booking, ActivityType.Gokart);
        Session session5 = new Session(LocalDate.now(), LocalTime.of(10,0,0), LocalTime.of(10,30,0), booking, ActivityType.Sumo);
        Session session6 = new Session(LocalDate.now(), LocalTime.of(12,0,0), LocalTime.of(13,0,0), booking, ActivityType.Minigolf);



        sessionRepo.save(session1);
        sessionRepo.save(session2);
        sessionRepo.save(session3);
        sessionRepo.save(session4);
        sessionRepo.save(session5);
        sessionRepo.save(session6);

    }
}