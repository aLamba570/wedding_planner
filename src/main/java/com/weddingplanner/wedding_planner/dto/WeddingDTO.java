package com.weddingplanner.wedding_planner.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
public class WeddingDTO {
    private String id;
    private String userId;
    private LocalDateTime weddingDate;
    private VenueDTO venue;
    private BudgetDTO budget;
    private List<GuestDTO> guests;
    private WeddingThemeDTO theme;
    private TimelineDTO timeline;
    private SeatingChartDTO seatingChart;

    @Data
    public static class VenueDTO {
        private String name;
        private AddressDTO address;
        private Integer capacity;
        private List<String> amenities;
        private String contactPerson;
        private String contactPhone;
    }

    @Data
    public static class AddressDTO {
        private String street;
        private String city;
        private String state;
        private String zipCode;
    }

    @Data
    public static class BudgetDTO {
        private Double totalBudget;
        private Map<String, BudgetItemDTO> items;
        private Double spentAmount;
        private Double remainingAmount;

        @Data
        public static class BudgetItemDTO {
            private String category;
            private Double allocatedAmount;
            private Double spentAmount;
            private String notes;
        }
    }

    @Data
    public static class WeddingThemeDTO {
        private String name;
        private List<String> colors;
        private String style;
        private List<String> decorElements;
    }

    @Data
    public static class TimelineDTO {
        private List<EventDTO> events;

        @Data
        public static class EventDTO {
            private LocalDateTime startTime;
            private LocalDateTime endTime;
            private String title;
            private String description;
            private String location;
            private List<String> responsible;
        }
    }

    @Data
    public static class SeatingChartDTO {
        private List<TableDTO> tables;

        @Data
        public static class TableDTO {
            private String tableNumber;
            private Integer capacity;
            private List<String> guestIds;
            private String category;
        }
    }
}