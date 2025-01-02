package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.ws.soap.addressing.server.annotation.Address;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "weddings")
public class Wedding {
    @Id
    private String id;
    private String userId;
    private LocalDateTime weddingDate;
    private Venue venue;
    private Budget budget;
    private List<Guest> guests;
    private List<Booking> bookings;
    private WeddingTheme theme;
    private Timeline timeline;
    private SeatingChart seatingChart;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class Venue {
        private String name;
        private String address;
        private Integer capacity;
        private List<String> amenities;
        private String contactPerson;
        private String contactPhone;
    }

    @Data
    public static class Budget {
        private Double totalBudget;
        private Map<String, BudgetItem> items;
        private Double spentAmount;
        private Double remainingAmount;

        @Data
        public static class BudgetItem {
            private String category;
            private Double allocatedAmount;
            private Double spentAmount;
            private String notes;
        }
    }

    @Data
    public static class WeddingTheme {
        private String name;
        private List<String> colors;
        private String style;
        private List<String> decorElements;
    }

    @Data
    public static class Timeline {
        private List<Event> events;

        @Data
        public static class Event {
            private LocalDateTime startTime;
            private LocalDateTime endTime;
            private String title;
            private String description;
            private String location;
            private List<String> responsible;
        }
    }

    @Data
    public static class SeatingChart {
        private List<Table> tables;

        @Data
        public static class Table {
            private String tableNumber;
            private Integer capacity;
            private List<String> guestIds;
            private String category; // e.g., "Family", "Friends", "Colleagues"
        }
    }
}