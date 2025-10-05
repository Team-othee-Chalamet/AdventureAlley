package org.example.adventurealley.catalog.model.activities;

import org.example.adventurealley.catalog.model.Activity;
import org.example.adventurealley.catalog.model.Session;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityPaintBall extends Activity {

    static int durationInHours = 3;
    static int durationInMinutes = 0;

    public static List<Session> getAvailableSessions(LocalDate startDate, LocalDate endDate){
        List<Session> generatedSessions = new ArrayList<>();

        LocalDate currentIterationDay = startDate; //Set a currentDay to differentiate between startDate and the current date having sessions added
        while(!currentIterationDay.equals(endDate.plusDays(1))){
            //There should only be two sessions of paintball in a day, so we add them manually, since we want a break in between

            generatedSessions.add(new Session(currentIterationDay, LocalTime.of(9,0,0), LocalTime.of(12,0,0), null, ActivityType.Paintball));

            generatedSessions.add(new Session(currentIterationDay, LocalTime.of(13,0,0), LocalTime.of(16,0,0), null, ActivityType.Paintball));



            currentIterationDay = currentIterationDay.plusDays(1);
        }


        return generatedSessions;
    }
}
