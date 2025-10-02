package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.catalog.dto.SessionMapper;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.repository.SessionRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SessionService {
    SessionRepo sessionRepo;

    public SessionService(SessionRepo sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    public List<SessionDTO> getAllSessions() {
        // Add all sessions to a list, create a list for sessionDTOs, map sessions to sessionDTOs
        List<Session> sessionsFound = sessionRepo.findAll();
        List<SessionDTO> sessionDTOs = new ArrayList<>();
        for (Session session : sessionsFound) {
            sessionDTOs.add(SessionMapper.toDTO(session));
        }
        return sessionDTOs;
    }

    public SessionDTO getSessionById(Long id) {
        // Find session by id, if it doesn't exist, throw an error, else, map it to DTO and return
        Optional<Session> foundSession = sessionRepo.findById(id);
        if (!foundSession.isPresent()) {
            throw new RuntimeException();
        }
        return SessionMapper.toDTO(foundSession.get());
    }
}
