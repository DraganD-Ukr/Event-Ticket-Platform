package com.dragand.event_ticket_platform_api.service.impl;

import com.dragand.event_ticket_platform_api.dto.CreateEventRequest;
import com.dragand.event_ticket_platform_api.exception.ResourceNotFoundException;
import com.dragand.event_ticket_platform_api.model.Event;
import com.dragand.event_ticket_platform_api.model.TicketType;
import com.dragand.event_ticket_platform_api.model.User;
import com.dragand.event_ticket_platform_api.repository.EventRepository;
import com.dragand.event_ticket_platform_api.repository.UserRepository;
import com.dragand.event_ticket_platform_api.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {

        User organizer = userRepository.findById(organizerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("User with ID %s not found", organizerId)
                ));

        List<TicketType> ticketTypesToCreate = event.getTicketTypes().stream().map(
                ticketType -> {
                    TicketType toCreate = new TicketType();
                    toCreate.setName(ticketType.getName());
                    toCreate.setPrice(ticketType.getPrice());
                    toCreate.setDescription(ticketType.getDescription());
                    toCreate.setTotalAvailable(ticketType.getTotalAvailable());
                    return toCreate;
                }
        ).toList();

        Event newEvent = new Event();

        newEvent.setName(event.getName());
        newEvent.setStartTime(event.getStartTime());
        newEvent.setEndTime(event.getEndTime());
        newEvent.setVenue(event.getVenue());
        newEvent.setSalesStart(event.getSalesStart());
        newEvent.setSalesEnd(event.getSalesEnd());
        newEvent.setStatus(event.getStatus());
        newEvent.setOrganizer(organizer);
        newEvent.setTicketTypes(ticketTypesToCreate);

        return eventRepository.save(newEvent);
    }



}
