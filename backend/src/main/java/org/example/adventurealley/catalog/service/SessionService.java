package org.example.adventurealley.catalog.service;

import org.example.adventurealley.catalog.dto.BookingMapper;
import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.catalog.dto.SessionMapper;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.model.activities.*;
import org.example.adventurealley.catalog.repository.SessionRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    /*
    public SessionDTO createSession(SessionDTO sessionDTO) {
        return SessionMapper.toDTO(sessionRepo.save(SessionMapper.toEntity(sessionDTO)));
    }
    */

    public List<SessionDTO> getAllSessionsInWeek(String startDateString, String endDateString){
        List<Session> allSessions = sessionRepo.findAll();
        List<Session> sessionsInSpan = new ArrayList<>();

        LocalDate startDate = LocalDate.parse(startDateString);
        System.out.println(startDate);
        LocalDate endDate = LocalDate.parse(endDateString);
        System.out.println(endDate);

        for (Session s: allSessions){
            if (s.getDate().isAfter(startDate.minusDays(1)) && s.getDate().isBefore(endDate.plusDays(1))){
                sessionsInSpan.add(s);
            }
        }
        //Sessions within the span have been found, now to find the sessions that are unbooked.
        List<Session> unbookedSessions = new ArrayList<>();
        unbookedSessions.addAll(ActivityFactory.getActivity(ActivityType.Gokart).getAvailableSessions(startDate, endDate));//Add all Gokart sessions
        unbookedSessions.addAll(ActivityFactory.getActivity(ActivityType.Sumo).getAvailableSessions(startDate, endDate));//Add all Paintball sessions
        unbookedSessions.addAll(ActivityFactory.getActivity(ActivityType.Paintball).getAvailableSessions(startDate, endDate));//Add all Sumo Sessions
        unbookedSessions.addAll(ActivityFactory.getActivity(ActivityType.Minigolf).getAvailableSessions(startDate, endDate));//Add all miniGolf Sessions

        List<Session> sessionsToRemove = new ArrayList<>();

        for (Session uBs: unbookedSessions){
            for (Session bS: sessionsInSpan){
                if(uBs.getDate().equals(bS.getDate()) && uBs.getStartTime().equals(bS.getStartTime()) && uBs.getActivityType().equals(bS.getActivityType())){
                    sessionsToRemove.add(uBs);
                }
            }
        }

        unbookedSessions.removeAll(sessionsToRemove);
        List<Session> completeSessionList = unbookedSessions;
        completeSessionList.addAll(sessionsInSpan);

        completeSessionList.sort(null);

        List<SessionDTO> convertedList = new ArrayList<>();

        for (Session s: completeSessionList){
            convertedList.add(SessionMapper.toDTO(s));
        }

        return convertedList;
    }
}