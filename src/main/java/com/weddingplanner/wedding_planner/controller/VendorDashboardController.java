package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import com.weddingplanner.wedding_planner.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors/dashboard")
@PreAuthorize("hasRole('VENDOR')")
@RequiredArgsConstructor
public class VendorDashboardController {
    private final VendorService vendorService;

    @GetMapping("/profile")
    public ResponseEntity<VendorProfile> getProfile() {
        return ResponseEntity.ok(vendorService.getCurrentVendorProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<VendorProfile> updateProfile(@RequestBody VendorProfile profile) {
        return ResponseEntity.ok(vendorService.updateVendorProfile(profile));
    }

    @GetMapping("/services")
    public ResponseEntity<List<String>> getServices() {
        return ResponseEntity.ok(vendorService.getCurrentVendorServices());
    }

    @PostMapping("/services")
    public ResponseEntity<Void> addService(@RequestBody String service) {
        vendorService.addService(service);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/services/{service}")
    public ResponseEntity<Void> removeService(@PathVariable String service) {
        vendorService.removeService(service);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getBookings() {
        return ResponseEntity.ok(vendorService.getVendorBookings());
    }
}
