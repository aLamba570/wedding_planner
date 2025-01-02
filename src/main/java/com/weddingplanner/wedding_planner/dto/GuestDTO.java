package com.weddingplanner.wedding_planner.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class GuestDTO {
    private String id;
    private String weddingId;

    @NotBlank(message = "Guest name is required")
    private String name;

    @Email(message = "Please provide a valid email address")
    private String email;

    private String phoneNumber;
    private String rsvpStatus; // PENDING, CONFIRMED, DECLINED, MAYBE
    private Integer partySize;
    private GuestPreferencesDTO preferences;
    private String tableAssignment;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class GuestPreferencesDTO {
        private List<String> dietaryRestrictions;
        private String mealChoice;
        private Boolean needsAccommodation;
        private Boolean needsTransportation;
        private String specialRequests;
    }
}

