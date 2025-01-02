package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.UserDTO;
import com.weddingplanner.wedding_planner.dto.VendorDTO;
import com.weddingplanner.wedding_planner.dto.WeddingDTO;
import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.service.UserService;
import com.weddingplanner.wedding_planner.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users/dashboard")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserDashboardController {
    private final UserService userService;
    private final VendorService vendorService;

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getProfile() {
        return ResponseEntity.ok(userService.getCurrentUserProfile());
    }

    @GetMapping("/weddings")
    public ResponseEntity<List<WeddingDTO>> getWeddings() {
        return ResponseEntity.ok(userService.getCurrentUserWeddings());
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(userService.getCurrentUserBookings());
    }

    @GetMapping("/vendors")
    public ResponseEntity<Page<VendorDTO>> searchVendors(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            Pageable pageable) {
        return ResponseEntity.ok(vendorService.searchVendors(category, location, pageable));
    }
}