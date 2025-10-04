package org.example.adventurealley.catalog.repository;

import org.example.adventurealley.catalog.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepo extends JpaRepository<Session, Long> {
}
