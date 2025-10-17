package org.example.adventurealley.catalog.model.activities;

import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityMiniGolf implements Activity {

    static boolean perPerson = true;
    static double price = 75.00;

    static int durationInHours = 1;

    public List<Session> getAvailableSessions(LocalDate startDate, LocalDate endDate){
        List<Session> generatedSessions = new ArrayList<>();

        LocalDate currentIterationDay = startDate; //Set a currentDay to differentiate between startDate and the current date having sessions added
        while(!currentIterationDay.equals(endDate.plusDays(1))){
            int hour = 9;
            for (int i = 0; i < 8; i++) { //There can be eight sessions in the day, so I add eight sessions to the list
                generatedSessions.add(new Session(currentIterationDay, LocalTime.of(hour,0,0), LocalTime.of(hour+durationInHours,0,0), null, ActivityType.Minigolf));
                hour += durationInHours;
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
