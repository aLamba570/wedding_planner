package com.weddingplanner.wedding_planner.dto;

import lombok.Data;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Data
public class VendorProfileDTO {
    private String id;
    private String userId;
    private String businessName;
    private String description;
    private List<String> services;
    private String category;
    private PriceRangeDTO priceRange;
    private String location;
    private ContactInfoDTO contactInfo;
    private BusinessHoursDTO businessHours;
    private List<String> portfolioImages;
    private Double rating;
    private Boolean isVerified;

    @Data
    public static class PriceRangeDTO {
        private Double minPrice;
        private Double maxPrice;
        private String currency;
        private String category;
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

    @Data
    public static class BusinessHoursDTO {
        private Map<DayOfWeek, DayScheduleDTO> schedule;
        private List<HolidayDTO> holidays;
    }

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
        private String date;
        private String description;
    }
}