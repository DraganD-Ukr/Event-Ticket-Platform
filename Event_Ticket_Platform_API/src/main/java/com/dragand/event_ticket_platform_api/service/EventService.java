package com.dragand.event_ticket_platform_api.service;

import com.dragand.event_ticket_platform_api.dto.CreateEventRequestDto;
import com.dragand.event_ticket_platform_api.model.Event;

import java.util.UUID;


public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequestDto createEventRequestDto);

}
