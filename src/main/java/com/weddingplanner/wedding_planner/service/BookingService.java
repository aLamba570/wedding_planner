package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.BookingDTO;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.BookingMapper;
import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.repository.BookingRepository;
import com.weddingplanner.wedding_planner.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final VendorRepository vendorRepository;

    @Transactional
    public BookingDTO createBooking(BookingDTO bookingDTO, String userId) {
        // Validate vendor exists
        vendorRepository.findById(bookingDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", bookingDTO.getVendorId()));

        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setUserId(userId);
        booking.setStatus(Booking.BookingStatus.PENDING);
        booking.setCreatedAt(LocalDateTime.now());
        booking.setUpdatedAt(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);
        return bookingMapper.toDTO(savedBooking);
    }

//    write code to getvendorbookings
    @Transactional(readOnly = true)
    public BookingDTO getVendorBookings(String id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", id));
        return bookingMapper.toDTO(booking);
    }
}