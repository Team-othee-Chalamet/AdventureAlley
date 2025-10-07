package org.example.adventurealley.catalog.repository;

import org.example.adventurealley.catalog.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShiftRepo extends JpaRepository<Shift, Long> {}
