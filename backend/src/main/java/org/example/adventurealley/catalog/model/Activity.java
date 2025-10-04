package org.example.adventurealley.catalog.model;

import jakarta.persistence.Entity;
import org.example.adventurealley.catalog.model.activities.ActivityFactory;
import org.example.adventurealley.common.baseClasses.BaseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Activity {

    static public List<Session> getAvailableSessions(LocalDate startDate, LocalDate endDate){
        return new ArrayList<>();
    }

}
