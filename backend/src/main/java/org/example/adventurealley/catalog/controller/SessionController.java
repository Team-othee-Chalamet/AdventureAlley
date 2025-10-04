package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.catalog.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping
    ResponseEntity<List<SessionDTO>> getAllSessions() {
        List<SessionDTO> sessionDTOList = sessionService.getAllSessions();
        return ResponseEntity.ok().body(sessionDTOList);
    }

    //A getMapping method for getting all possible sessions in a week, with any booked sessions marked as booked
    //Optional objective: Can be filtered per activity
    @GetMapping("/{startDate}/{endDate}")
    ResponseEntity<List<SessionDTO>> getSessionsByWeekNumber(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) {
        System.out.println("Trying to find all sessions within: " + startDate + " and: " + endDate);
        List<SessionDTO> listOfAllSessionsInFrame = sessionService.getAllSessionsInWeek(startDate, endDate);
        return ResponseEntity.ok(listOfAllSessionsInFrame);
    }

    @GetMapping("/{id}")
    ResponseEntity<SessionDTO> getSessionByID(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessionService.getSessionById(id));
    }
    /* We don't really create a session all by itself, when a session is created, it is because it is in a booking. Now, when a booking is created, that endpoint takes care of saving relevant sessions to the DB.
    @PostMapping
    ResponseEntity<SessionDTO> createSession(@RequestBody SessionDTO sessionDTO) {
        return ResponseEntity.ok().body(sessionService.createSession(sessionDTO));
    }

     */
}
