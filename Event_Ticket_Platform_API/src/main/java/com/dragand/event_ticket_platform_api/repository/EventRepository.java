package com.dragand.event_ticket_platform_api.repository;

import com.dragand.event_ticket_platform_api.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
}
