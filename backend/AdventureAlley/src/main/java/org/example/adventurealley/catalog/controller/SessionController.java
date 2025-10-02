package org.example.adventurealley.catalog.controller;

import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.service.SessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
