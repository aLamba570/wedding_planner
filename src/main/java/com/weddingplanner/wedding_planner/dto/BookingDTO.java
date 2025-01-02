package com.weddingplanner.wedding_planner.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BookingDTO {
    private String id;

    @NotBlank(message = "Wedding ID is required")
    private String weddingId;

    @NotBlank(message = "Vendor ID is required")
    private String vendorId;

    private String userId;
    private LocalDateTime bookingDate;
    private String status; // PENDING, CONFIRMED, CANCELLED, COMPLETED

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;

    private String service;
    private PaymentDTO payment;
    private BookingDetailsDTO details;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class BookingDetailsDTO {
        private LocalDateTime serviceDate;
        private String location;
        private String specialRequirements;
        private List<String> includedItems;
        private List<String> additionalNotes;
    }
}