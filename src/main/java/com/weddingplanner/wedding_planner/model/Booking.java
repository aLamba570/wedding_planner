package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "bookings")
public class Booking {
    @Id
    private String id;
    private String weddingId;
    private String vendorId;
    private String userId;
    private LocalDateTime bookingDate;
    private BookingStatus status;
    private Double amount;
    private String service;
    private Payment payment;
    private BookingDetails details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum BookingStatus {
        PENDING, CONFIRMED, CANCELLED, COMPLETED
    }

    @Data
    public static class BookingDetails {
        private LocalDateTime serviceDate;
        private String location;
        private String specialRequirements;
        private List<String> includedItems;
        private List<String> additionalNotes;
    }
}