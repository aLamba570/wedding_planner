package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.WeddingDTO;
import com.weddingplanner.wedding_planner.model.Wedding;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        uses = {GuestMapper.class, BookingMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WeddingMapper {

    @Mapping(target = "guests", source = "guests")
    @Mapping(target = "venue", source = "venue")
    @Mapping(target = "budget", source = "budget")
    @Mapping(target = "theme", source = "theme")
    @Mapping(target = "timeline", source = "timeline")
    @Mapping(target = "seatingChart", source = "seatingChart")
    WeddingDTO toDTO(Wedding wedding);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "bookings", ignore = true) // Since it's not in DTO
    Wedding toEntity(WeddingDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    void updateEntity(WeddingDTO dto, @MappingTarget Wedding wedding);

    // Venue mappings
    @Mapping(target = "address", source = "address")
    Wedding.Venue mapVenue(WeddingDTO.VenueDTO dto);

    @Mapping(target = "address", source = "address")
    WeddingDTO.VenueDTO mapVenueToDto(Wedding.Venue venue);

    // Address mappings
    default String mapAddressToString(WeddingDTO.AddressDTO address) {
        if (address == null) return null;
        return String.format("%s, %s, %s, %s",
                address.getStreet(),
                address.getCity(),
                address.getState(),
                address.getZipCode());
    }

    default WeddingDTO.AddressDTO mapStringToAddress(String address) {
        if (address == null) return null;
        WeddingDTO.AddressDTO dto = new WeddingDTO.AddressDTO();
        String[] parts = address.split(", ");
        if (parts.length >= 4) {
            dto.setStreet(parts[0]);
            dto.setCity(parts[1]);
            dto.setState(parts[2]);
            dto.setZipCode(parts[3]);
        }
        return dto;
    }

    // Budget mappings
    Wedding.Budget mapBudget(WeddingDTO.BudgetDTO dto);
    WeddingDTO.BudgetDTO mapBudgetToDto(Wedding.Budget budget);

    Wedding.Budget.BudgetItem mapBudgetItem(WeddingDTO.BudgetDTO.BudgetItemDTO dto);
    WeddingDTO.BudgetDTO.BudgetItemDTO mapBudgetItemToDto(Wedding.Budget.BudgetItem entity);

    // Theme mappings
    Wedding.WeddingTheme mapTheme(WeddingDTO.WeddingThemeDTO dto);
    WeddingDTO.WeddingThemeDTO mapThemeToDto(Wedding.WeddingTheme theme);

    // Timeline mappings
    Wedding.Timeline mapTimeline(WeddingDTO.TimelineDTO dto);
    WeddingDTO.TimelineDTO mapTimelineToDto(Wedding.Timeline timeline);

    Wedding.Timeline.Event mapTimelineEvent(WeddingDTO.TimelineDTO.EventDTO dto);
    WeddingDTO.TimelineDTO.EventDTO mapTimelineEventToDto(Wedding.Timeline.Event entity);

    // Seating chart mappings
    Wedding.SeatingChart mapSeatingChart(WeddingDTO.SeatingChartDTO dto);
    WeddingDTO.SeatingChartDTO mapSeatingChartToDto(Wedding.SeatingChart seatingChart);

    Wedding.SeatingChart.Table mapTable(WeddingDTO.SeatingChartDTO.TableDTO dto);
    WeddingDTO.SeatingChartDTO.TableDTO mapTableToDto(Wedding.SeatingChart.Table entity);
}