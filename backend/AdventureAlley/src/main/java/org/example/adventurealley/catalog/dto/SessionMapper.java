package org.example.adventurealley.catalog.dto;

import org.example.adventurealley.catalog.model.Session;

public class SessionMapper {

    public static SessionDTO toDTO(Session session) {
        SessionDTO sessionDTO = new SessionDTO(session.getDate(), session.getTimeslot(), session.getSessionActivity(), session.getBooking());
        return sessionDTO;
    }

    static public Session toEntity(SessionDTO sessionDTO) {
        Session session = new Session(sessionDTO.date(), sessionDTO.timeslot(), sessionDTO.sessionActivity(), sessionDTO.booking());
        return session;
    }
}
