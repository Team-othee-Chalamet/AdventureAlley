package org.example.adventurealley.catalog.model.activities;

import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ActivitySumo implements Activity {

    static boolean perPerson = false;
    static double price = 300.00;

    static int durationInHours = 1;
    static int durationInMinutes = 30;

    public List<Session> getAvailableSessions(LocalDate startDate, LocalDate endDate){
        List<Session> generatedSessions = new ArrayList<>();

        LocalDate currentIterationDay = startDate; //Set a currentDay to differentiate between startDate and the current date having sessions added
        while(!currentIterationDay.equals(endDate.plusDays(1))){
            LocalTime time = LocalTime.of(9,0,0);

            for (int i = 0; i < 16; i++) {
                generatedSessions.add(new Session(currentIterationDay, time, time.plusMinutes(durationInMinutes), null, ActivityType.Sumo));

                time = time.plusMinutes(durationInMinutes);
            }

            currentIterationDay = currentIterationDay.plusDays(1);
        }


        return generatedSessions;
    }

    public double getPrice() {
        return price;
    }

    public boolean isPerPerson(){
        return perPerson;
    }
}
