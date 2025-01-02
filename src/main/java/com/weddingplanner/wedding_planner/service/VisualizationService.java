package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.model.Review;
import com.weddingplanner.wedding_planner.repository.BookingRepository;
import com.weddingplanner.wedding_planner.repository.ReviewRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisualizationService {
    private final BookingRepository bookingRepository;
    private final ReviewRepository reviewRepository;

    public List<ChartData> getRevenueChartData(String vendorId, int months) {
        LocalDateTime startDate = LocalDateTime.now().minusMonths(months);
        List<Booking> bookings = bookingRepository.findByVendorIdAndBookingDateAfter(vendorId, startDate);

        return bookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getBookingDate().format(DateTimeFormatter.ofPattern("MMM yyyy")),
                        Collectors.summingDouble(Booking::getAmount)
                ))
                .entrySet().stream()
                .map(entry -> new ChartData(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(ChartData::getLabel))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getBookingStatusDistribution(String vendorId) {
        List<Booking> bookings = bookingRepository.findByVendorId(vendorId);
        return bookings.stream()
                .collect(Collectors.groupingBy(
                        booking -> booking.getStatus().toString(),
                        Collectors.counting()
                ));
    }

    public List<ChartData> getRatingDistribution(String vendorId) {
        List<Review> reviews = reviewRepository.findByVendorId(vendorId);
        return reviews.stream()
                .collect(Collectors.groupingBy(
                        Review::getRating,
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> new ChartData(entry.getKey().toString(), entry.getValue().doubleValue()))
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class ChartData {
        private String label;
        private double value;
    }

}