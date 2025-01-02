package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "guests")
public class Guest {
    @Id
    private String id;
    private String weddingId;
    private String name;
    private String email;
    private String phoneNumber;
    private RsvpStatus rsvpStatus;
    private Integer partySize;
    private GuestPreferences preferences;
    private String tableAssignment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum RsvpStatus {
        PENDING, CONFIRMED, DECLINED, MAYBE
    }

    @Data
    public static class GuestPreferences {
        private List<String> dietaryRestrictions;
        private String mealChoice;
        private Boolean needsAccommodation;
        private Boolean needsTransportation;
        private String specialRequests;
    }
}