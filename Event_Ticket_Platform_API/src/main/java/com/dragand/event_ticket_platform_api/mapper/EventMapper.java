package com.dragand.event_ticket_platform_api.mapper;

import com.dragand.event_ticket_platform_api.dto.CreateEventResponseDto;
import com.dragand.event_ticket_platform_api.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateEventResponseDto toDto(Event event);



}
