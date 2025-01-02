package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.BookingDTO;
import com.weddingplanner.wedding_planner.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(
            @RequestBody BookingDTO bookingDTO,
            @RequestAttribute String userId) {
        return ResponseEntity.ok(bookingService.createBooking(bookingDTO, userId));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<BookingDTO>> getVendorBookings(@PathVariable String vendorId) {
        return ResponseEntity.ok(Collections.singletonList(bookingService.getVendorBookings(vendorId)));
    }
}