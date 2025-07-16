package com.dragand.event_ticket_platform_api.controller;

import com.dragand.event_ticket_platform_api.dto.CreateEventRequestDto;
import com.dragand.event_ticket_platform_api.dto.CreateEventResponseDto;
import com.dragand.event_ticket_platform_api.mapper.EventMapper;
import com.dragand.event_ticket_platform_api.model.Event;
import com.dragand.event_ticket_platform_api.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final EventMapper eventMapper;
    private final EventService eventService;


    @PostMapping
    public ResponseEntity<CreateEventResponseDto> createEvent(
            @AuthenticationPrincipal Jwt jwt,
            @Valid @RequestBody CreateEventRequestDto createEventRequestDto
    ) {
        UUID callerUserId = UUID.fromString(jwt.getSubject());
        Event createdEvent = eventService.createEvent(callerUserId, createEventRequestDto);

        CreateEventResponseDto responseDto = eventMapper.toDto(createdEvent);
        return ResponseEntity.ok(responseDto);
    }

}
