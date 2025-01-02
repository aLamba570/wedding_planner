package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "vendor_profiles")
public class VendorProfile {
    @Id
    private String id;
    private String userId;
    private String businessName;
    private String description;
    private List<String> services;
    private String category;
    private PriceRange priceRange;
    private String location;
    private ContactInfo contactInfo;
    private BusinessHours businessHours;
    private List<String> portfolioImages;
    private Double rating;
    private Boolean isVerified;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void setEmail(String email) {
        this.contactInfo.setEmail(email);
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
    public static class PriceRange {
        private Double minPrice;
        private Double maxPrice;
        private String currency;
        private PriceCategory category;

        public enum PriceCategory {
            BUDGET, MODERATE, PREMIUM, LUXURY
        }
    }

    @Data
    public static class BusinessHours {
        private Map<DayOfWeek, DaySchedule> schedule;
        private List<Holiday> holidays;

        @Data
        public static class DaySchedule {
            private LocalTime openTime;
            private LocalTime closeTime;
            private boolean closed;
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
            private LocalDateTime date;
            private String description;
        }
    }
}