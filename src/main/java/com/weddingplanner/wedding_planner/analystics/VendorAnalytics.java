package com.weddingplanner.wedding_planner.analystics;

import lombok.Data;

import java.util.List;

@Data
public class VendorAnalytics {
    private BookingStats bookingStats;
    private RevenueStats revenueStats;
    private List<MonthlyRevenue> monthlyRevenue;
    private List<PopularService> popularServices;
    private CustomerStats customerStats;

    @Data
    public static class BookingStats {
        private int totalBookings;
        private int pendingBookings;
        private int completedBookings;
        private int cancelledBookings;
        private double bookingConversionRate;
    }

    @Data
    public static class RevenueStats {
        private double totalRevenue;
        private double averageBookingValue;
        private double revenueThisMonth;
        private double revenueGrowth;
    }

    @Data
    public static class MonthlyRevenue {
        private String month;
        private double revenue;
        private int bookings;
    }

    @Data
    public static class PopularService {
        private String serviceName;
        private int bookingCount;
        private double totalRevenue;
    }

    @Data
    public static class CustomerStats {
        private int totalCustomers;
        private int repeatCustomers;
        private double averageRating;
        private int totalReviews;
    }
}