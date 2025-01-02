package com.weddingplanner.wedding_planner.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.time.DayOfWeek;

@Data
public class VendorDTO {
    private String id;
    private String businessName;
    private String category;
    private String description;
    private List<String> services;
    private Double rating;
    private PriceRangeDTO priceRange;
    private List<String> portfolioImages;
    private ContactInfoDTO contactInfo;
    private BusinessHoursDTO businessHours;

    @Data
    public static class PriceRangeDTO {
        private Double minPrice;
        private Double maxPrice;
        private String currency;
        private PriceCategoryDTO category;

        public enum PriceCategoryDTO {
            BUDGET, MODERATE, PREMIUM, LUXURY
        }
    }

    @Data
    public static class BusinessHoursDTO {
        private Map<DayOfWeek, DayScheduleDTO> schedule;
        private List<HolidayDTO> holidays;

        @Data
        public static class DayScheduleDTO {
            private LocalTime openTime;
            private LocalTime closeTime;
            private boolean closed;
            private List<BreakDTO> breaks;
        }

        @Data
        public static class BreakDTO {
            private LocalTime startTime;
            private LocalTime endTime;
            private String description;
        }

        @Data
        public static class HolidayDTO {
            private LocalDate date;
            private String description;
        }
    }

    @Data
    public static class ContactInfoDTO {
        private String email;
        private String phone;
        private String website;
        private AddressDTO address;
        private List<String> alternativePhones;
        private SocialMediaDTO socialMedia;
    }

    @Data
    public static class AddressDTO {
        private String street;
        private String city;
        private String state;
        private String country;
        private String zipCode;
        private Double latitude;
        private Double longitude;
    }

    @Data
    public static class SocialMediaDTO {
        private String facebook;
        private String instagram;
        private String twitter;
        private String linkedin;
    }
}