package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.analystics.VendorAnalytics;
import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.model.Review;
import com.weddingplanner.wedding_planner.repository.BookingRepository;
import com.weddingplanner.wedding_planner.repository.ReviewRepository;
import com.weddingplanner.wedding_planner.repository.VendorProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;
    private final VendorProfileRepository vendorProfileRepository;

    public VendorAnalytics getVendorAnalytics(String vendorId) {
        VendorAnalytics analytics = new VendorAnalytics();

        // Calculate booking stats
        analytics.setBookingStats(calculateBookingStats(vendorId));

        // Calculate revenue stats
        analytics.setRevenueStats(calculateRevenueStats(vendorId));

        // Calculate monthly revenue
        analytics.setMonthlyRevenue(calculateMonthlyRevenue(vendorId));

        // Calculate popular services
        analytics.setPopularServices(calculatePopularServices(vendorId));

        // Calculate customer stats
        analytics.setCustomerStats(calculateCustomerStats(vendorId));

        return analytics;
    }

    private VendorAnalytics.CustomerStats calculateCustomerStats(String vendorId) {
        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);
        List<Review> reviews = reviewRepository.findByVendorId(vendorId);

        VendorAnalytics.CustomerStats stats = new VendorAnalytics.CustomerStats();

        // Calculate total unique customers
        Set<String> uniqueCustomers = bookings.stream()
                .map(Booking::getUserId)
                .collect(Collectors.toSet());
        stats.setTotalCustomers(uniqueCustomers.size());

        // Calculate repeat customers (customers with more than one booking)
        long repeatCustomers = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getUserId))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .count();
        stats.setRepeatCustomers((int) repeatCustomers);

        // Calculate average rating
        if (!reviews.isEmpty()) {
            double avgRating = reviews.stream()
                    .mapToInt(Review::getRating)
                    .average()
                    .orElse(0.0);
            stats.setAverageRating(avgRating);
        }

        stats.setTotalReviews(reviews.size());

        return stats;
    }

    private List<VendorAnalytics.PopularService> calculatePopularServices(String vendorId) {
        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);

        // Group bookings by service and count them
        Map<String, List<Booking>> serviceBookings = bookings.stream()
                .collect(Collectors.groupingBy(Booking::getService));

        return serviceBookings.entrySet().stream()
                .map(entry -> {
                    VendorAnalytics.PopularService service = new VendorAnalytics.PopularService();
                    service.setServiceName(entry.getKey());
                    service.setBookingCount(entry.getValue().size());
                    service.setTotalRevenue(entry.getValue().stream()
                            .mapToDouble(Booking::getAmount)
                            .sum());
                    return service;
                })
                .sorted((s1, s2) -> Integer.compare(s2.getBookingCount(), s1.getBookingCount()))
                .collect(Collectors.toList());
    }

    private List<VendorAnalytics.MonthlyRevenue> calculateMonthlyRevenue(String vendorId) {
        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);

        // Group bookings by month
        Map<Month, List<Booking>> monthlyBookings = bookings.stream()
                .filter(booking -> booking.getBookingDate().isAfter(sixMonthsAgo))
                .collect(Collectors.groupingBy(booking -> booking.getBookingDate().getMonth()));

        return monthlyBookings.entrySet().stream()
                .map(entry -> {
                    VendorAnalytics.MonthlyRevenue monthlyRevenue = new VendorAnalytics.MonthlyRevenue();
                    monthlyRevenue.setMonth(entry.getKey().toString());
                    monthlyRevenue.setRevenue(entry.getValue().stream()
                            .mapToDouble(Booking::getAmount)
                            .sum());
                    monthlyRevenue.setBookings(entry.getValue().size());
                    return monthlyRevenue;
                })
                .sorted((m1, m2) -> Month.valueOf(m1.getMonth()).compareTo(Month.valueOf(m2.getMonth())))
                .collect(Collectors.toList());
    }

    private VendorAnalytics.BookingStats calculateBookingStats(String vendorId) {
        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);
        VendorAnalytics.BookingStats stats = new VendorAnalytics.BookingStats();

        stats.setTotalBookings(bookings.size());
        stats.setPendingBookings((int) bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.PENDING)
                .count());
        stats.setCompletedBookings((int) bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.COMPLETED)
                .count());
        stats.setCancelledBookings((int) bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.CANCELLED)
                .count());

        // Calculate booking conversion rate
        if (stats.getTotalBookings() > 0) {
            double conversionRate = (double) stats.getCompletedBookings() / stats.getTotalBookings() * 100;
            stats.setBookingConversionRate(conversionRate);
        }

        return stats;
    }

    private VendorAnalytics.RevenueStats calculateRevenueStats(String vendorId) {
        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);
        VendorAnalytics.RevenueStats stats = new VendorAnalytics.RevenueStats();

        // Calculate total revenue from completed bookings
        double totalRevenue = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.COMPLETED)
                .mapToDouble(Booking::getAmount)
                .sum();

        stats.setTotalRevenue(totalRevenue);

        // Calculate average booking value
        if (!bookings.isEmpty()) {
            stats.setAverageBookingValue(totalRevenue / bookings.size());
        }

        // Calculate this month's revenue
        double thisMonthRevenue = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.COMPLETED)
                .filter(b -> b.getBookingDate().getMonth() == LocalDateTime.now().getMonth())
                .mapToDouble(Booking::getAmount)
                .sum();
        stats.setRevenueThisMonth(thisMonthRevenue);

        // Calculate revenue growth (compared to previous month)
        LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);
        double lastMonthRevenue = bookings.stream()
                .filter(b -> b.getStatus() == Booking.BookingStatus.COMPLETED)
                .filter(b -> b.getBookingDate().getMonth() == lastMonth.getMonth())
                .mapToDouble(Booking::getAmount)
                .sum();

        if (lastMonthRevenue > 0) {
            double growth = ((thisMonthRevenue - lastMonthRevenue) / lastMonthRevenue) * 100;
            stats.setRevenueGrowth(growth);
        }

        return stats;
    }


}