package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.VendorDTO;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.VendorMapper;
import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.model.Vendor;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import com.weddingplanner.wedding_planner.repository.BookingRepository;
import com.weddingplanner.wedding_planner.repository.VendorProfileRepository;
import com.weddingplanner.wedding_planner.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendorService {
    private final VendorRepository vendorRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final UserService userService;
    private final VendorMapper vendorMapper;
    private final BookingRepository bookingRepository;

    @Transactional
    public VendorProfile getCurrentVendorProfile() {
        String userId = userService.getCurrentUserId();
        return vendorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("VendorProfile", "userId", userId));
    }

    public VendorProfile getVendorProfileByUserId(String userId) {
        return vendorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("VendorProfile", "userId", userId));
    }

    @Transactional
    public VendorProfile updateVendorProfile(VendorProfile profile) {
        String userId = userService.getCurrentUserId();
        VendorProfile existingProfile = vendorProfileRepository.findByUserId(userId)
                .orElse(new VendorProfile());

        existingProfile.setBusinessName(profile.getBusinessName());
        existingProfile.setDescription(profile.getDescription());
        existingProfile.setCategory(profile.getCategory());
        existingProfile.setLocation(profile.getLocation());
        existingProfile.setPriceRange(profile.getPriceRange());
        existingProfile.setContactInfo(profile.getContactInfo());
        existingProfile.setBusinessHours(profile.getBusinessHours());
        existingProfile.setPortfolioImages(profile.getPortfolioImages());
        existingProfile.setUpdatedAt(LocalDateTime.now());

        return vendorProfileRepository.save(existingProfile);
    }

    @Transactional
    public List<String> getCurrentVendorServices() {
        VendorProfile profile = getCurrentVendorProfile();
        return profile.getServices() != null ? profile.getServices() : new ArrayList<>();
    }

    @Transactional
    public void addService(String service) {
        VendorProfile profile = getCurrentVendorProfile();
        if (profile.getServices() == null) {
            profile.setServices(new ArrayList<>());
        }
        profile.getServices().add(service);
        vendorProfileRepository.save(profile);
    }

    @Transactional
    public void removeService(String service) {
        VendorProfile profile = getCurrentVendorProfile();
        if (profile.getServices() != null) {
            profile.getServices().remove(service);
            vendorProfileRepository.save(profile);
        }
    }

    @Transactional
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.toEntity(vendorDTO);
        vendor.setCreatedAt(LocalDateTime.now());
        vendor.setUpdatedAt(LocalDateTime.now());

        Vendor savedVendor = vendorRepository.save(vendor);
        return vendorMapper.toDTO(savedVendor);
    }

    @Transactional(readOnly = true)
    public Page<VendorDTO> searchVendors(String category, String location, Pageable pageable) {
        Page<Vendor> vendors;
        if (category != null && location != null) {
            vendors = vendorRepository.findByLocationAndCategory(location, category, pageable);
        } else if (category != null) {
            vendors = (Page<Vendor>) vendorRepository.findByCategory(category, pageable);
        } else {
            vendors = vendorRepository.findAll(pageable);
        }
        return vendors.map(vendorMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<Booking> getVendorBookings() {
        String userId = userService.getCurrentUserId();
        VendorProfile vendorProfile = vendorProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("VendorProfile", "userId", userId));

        return bookingRepository.findByVendorId(vendorProfile.getId())
                .stream()
                .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
                .toList();

    }
}