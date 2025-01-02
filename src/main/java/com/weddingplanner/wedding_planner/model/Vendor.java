package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "vendors")
public class Vendor {
    @Id
    private String id;
    private String businessName;
    private String category;
    private String description;
    private List<String> services;
    private List<Review> reviews;
    private String location;
    private Double rating;
    private PriceRange priceRange;
    private List<String> portfolioImages;
    private ContactInfo contactInfo;
    private BusinessHours businessHours;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Data
    public static class PriceRange {
        private Double minPrice;
        private Double maxPrice;
        private String currency = "USD";  // Default value
        private PriceCategory category;

        public enum PriceCategory {
            BUDGET("$"),
            MODERATE("$$"),
            PREMIUM("$$$"),
            LUXURY("$$$$");

            private final String symbol;

            PriceCategory(String symbol) {
                this.symbol = symbol;
            }

            public String getSymbol() {
                return symbol;
            }
        }
    }

    @Data
    public static class ContactInfo {
        private String email;
        private String phone;
        private String website;
        private Address address;
        private List<String> alternativePhones;
        private SocialMedia socialMedia;
    }

    @Data
    public static class Address {
        private String street;
        private String city;
        private String state;
        private String country;
        private String zipCode;
        private Double latitude;
        private Double longitude;
    }

    @Data
    public static class SocialMedia {
        private String facebook;
        private String instagram;
        private String twitter;
        private String linkedin;
    }

    @Data
    public static class BusinessHours {
        private Map<DayOfWeek, DaySchedule> schedule;
        private List<Holiday> holidays;

        @Data
        public static class DaySchedule {
            private LocalTime openTime;
            private LocalTime closeTime;
            private boolean closed;  // Changed from isClosed to closed
            private List<Break> breaks;
        }

        @Data
        public static class Break {
            private LocalTime startTime;
            private LocalTime endTime;
            private String description;
        }

        @Data
        public static class Holiday {
            private LocalDate date;
            private String description;
        }
    }
}