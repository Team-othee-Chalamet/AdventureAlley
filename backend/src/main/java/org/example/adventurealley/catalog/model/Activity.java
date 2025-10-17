package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import org.example.adventurealley.catalog.model.activities.ActivityFactory;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface Activity {

    public List<Session> getAvailableSessions(LocalDate startDate, LocalDate endDate);

    public double getPrice();

    public boolean isPerPerson();

}
