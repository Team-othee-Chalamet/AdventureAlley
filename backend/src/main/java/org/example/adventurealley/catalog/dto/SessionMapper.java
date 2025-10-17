package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;

public class SessionMapper {

    public static SessionDTO toDTO(Session session) {
        SessionDTO sessionDTO = new SessionDTO(session.getDate(), session.getStartTime(), session.getEndTime(), session.getActivityType(), session.getPrice(), session.getActivity().isPerPerson(), session.getBookingStatus());
        return sessionDTO;
    }

    static public Session toEntity(SessionDTO sessionDTO, Booking booking) {
        Session session = new Session(sessionDTO.date(), sessionDTO.startTime(), sessionDTO.endTime(), booking, sessionDTO.activityType());
        return session;
    }
}
