package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends MongoRepository<Booking, String> {
    List<Booking> findByWeddingId(String weddingId);
    List<Booking> findByVendorId(String vendorId);
    List<Booking> findByUserId(String userId);
    List<Booking> findByStatus(Booking.BookingStatus status);
    List<Booking> findByBookingDateBetween(LocalDateTime start, LocalDateTime end);

    List<Booking> findByVendorIdAndBookingDateAfter(String vendorId, LocalDateTime startDate);
}
