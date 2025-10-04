package org.example.adventurealley.catalog.repository;

import org.example.adventurealley.catalog.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
