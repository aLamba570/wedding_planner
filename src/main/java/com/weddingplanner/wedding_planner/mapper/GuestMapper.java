package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.GuestDTO;
import com.weddingplanner.wedding_planner.model.Guest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    GuestDTO toDTO(Guest guest);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Guest toEntity(GuestDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(GuestDTO dto, @MappingTarget Guest guest);

    // Enum mapping
    default Guest.RsvpStatus mapRsvpStatus(String status) {
        return status != null ? Guest.RsvpStatus.valueOf(status) : null;
    }

    default String mapRsvpStatusToString(Guest.RsvpStatus status) {
        return status != null ? status.name() : null;
    }
}