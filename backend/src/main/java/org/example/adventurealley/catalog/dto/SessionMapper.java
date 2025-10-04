package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;

public class SessionMapper {

    public static SessionDTO toDTO(Session session) {
        SessionDTO sessionDTO = new SessionDTO(session.getDate(), session.getTimeslot(), session.getSessionActivity());
        return sessionDTO;
    }

    static public Session toEntity(SessionDTO sessionDTO, Booking booking) {
        Session session = new Session(sessionDTO.date(), sessionDTO.timeslot(), sessionDTO.sessionActivity(), booking);
        return session;
    }
}
