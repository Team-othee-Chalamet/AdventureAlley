package org.example.adventurealley.catalog.model.activities;

import org.example.adventurealley.catalog.model.Activity;
import org.springframework.stereotype.Component;

@Component
public class ActivityFactory {
    static private ActivityMiniGolf miniGolf = new ActivityMiniGolf();
    static private ActivityGoKart goKart = new ActivityGoKart();
    static private ActivitySumo sumo = new ActivitySumo();
    static private ActivityPaintBall paintBall = new ActivityPaintBall();


    public static Activity getActivity(ActivityType activityType){
        switch(activityType){
            case ActivityType.Gokart:
                return goKart;
            case ActivityType.Minigolf:
                return miniGolf;
            case ActivityType.Paintball:
                return paintBall;
            case ActivityType.Sumo:
                return sumo;
            default:
                throw new RuntimeException();
        }
    }

}
