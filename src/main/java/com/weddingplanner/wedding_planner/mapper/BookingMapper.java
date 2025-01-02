package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.BookingDTO;
import com.weddingplanner.wedding_planner.model.Booking;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class})
public interface BookingMapper {
    @Mapping(target = "payment", source = "payment")
    BookingDTO toDTO(Booking booking);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Booking toEntity(BookingDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(BookingDTO dto, @MappingTarget Booking booking);

    // Enum mappings
    default Booking.BookingStatus mapBookingStatus(String status) {
        return status != null ? Booking.BookingStatus.valueOf(status) : null;
    }

    default String mapBookingStatusToString(Booking.BookingStatus status) {
        return status != null ? status.name() : null;
    }
}