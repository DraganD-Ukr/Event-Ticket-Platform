package com.dragand.event_ticket_platform_api.service;

import com.dragand.event_ticket_platform_api.dto.CreateEventRequest;
import com.dragand.event_ticket_platform_api.model.Event;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface EventService {

    Event createEvent(UUID organizerId, CreateEventRequest createEventRequest);

}
