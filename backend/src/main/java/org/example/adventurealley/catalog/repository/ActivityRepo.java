package org.example.adventurealley.catalog.repository;

import org.example.adventurealley.catalog.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepo extends JpaRepository<Activity, Long> {
}
