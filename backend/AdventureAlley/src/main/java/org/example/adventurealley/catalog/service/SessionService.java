package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.catalog.dto.SessionMapper;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.repository.SessionRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {
    SessionRepo sessionRepo;

    public SessionService(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    public List<SessionDTO> getAllSessions() {
        List<Session> sessionsFound = sessionRepo.findAll();
        List<SessionDTO> sessionDTOs = new ArrayList<>();
        for (Session session : sessionsFound) {
            sessionDTOs.add(SessionMapper.toDTO(session));
        }
        return sessionDTOs;
    }
}
