package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.VendorDTO;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import com.weddingplanner.wedding_planner.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vendors")
@RequiredArgsConstructor
public class VendorController {
    private final VendorService vendorService;

    @PostMapping
    public ResponseEntity<VendorDTO> createVendor(@RequestBody VendorDTO vendorDTO) {
        return ResponseEntity.ok(vendorService.createVendor(vendorDTO));
    }

    public ResponseEntity<?> getCurrentProfile() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String userId = auth.getName();
            VendorProfile profile = vendorService.getVendorProfileByUserId(userId);
            return ResponseEntity.ok(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<VendorProfile> updateProfile(@RequestBody VendorProfile profile) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userId = auth.getName();
        profile.setUserId(userId);
        return ResponseEntity.ok(vendorService.updateVendorProfile(profile));
    }

    @GetMapping
    public ResponseEntity<Page<VendorDTO>> searchVendors(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {

        Sort.Direction direction = Sort.Direction.fromString(sortDirection.toUpperCase());
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(direction, sortBy));

        return ResponseEntity.ok(vendorService.searchVendors(category, location, pageRequest));
    }
}