package org.example.adventurealley;

import org.example.adventurealley.catalog.dto.SessionDTO;
import org.example.adventurealley.catalog.model.Booking;
import org.example.adventurealley.catalog.model.Session;
import org.example.adventurealley.catalog.model.activities.*;
import org.example.adventurealley.catalog.repository.SessionRepo;
import org.example.adventurealley.catalog.service.SessionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepo sessionRepo;

    @InjectMocks
    private SessionService sessionService;


    @Test
    void testGetSessionsInSpanGettingNoBookedSessions(){
        //Arrange
        String startDate = "2025-10-05";
        String endDate = "2025-10-07";
        when(sessionRepo.findAll()).thenReturn(new ArrayList<>());

        //Act
        List<SessionDTO> sessions = sessionService.getAllSessionsInWeek(startDate, endDate);

        //Assert
        assertTrue(sessions.size() == 90);
    }

    @Test
    void testGetSessionsInSpanGettingBookedSessions(){
        //Arrange
        String startDate = "2025-10-05";
        String endDate = "2025-10-07";

        Session s1 = new Session(LocalDate.of(2025,10,6), LocalTime.of(9,0,0), LocalTime.of(11,0,0), new Booking(), ActivityType.Gokart);
        when(sessionRepo.findAll()).thenReturn(List.of(s1));

        //Act
        List<SessionDTO> sessions = sessionService.getAllSessionsInWeek(startDate, endDate);

        //Assert
        assertTrue(sessions.size() == 90);
    }
}
