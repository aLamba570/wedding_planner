package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.VendorProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VendorProfileRepository extends MongoRepository<VendorProfile, String> {
    Optional<VendorProfile> findByUserId(String userId);
    boolean existsByUserId(String userId);
    void deleteByUserId(String userId);

    // Custom queries for searching vendors
    List<VendorProfile> findByCategory(String category);
    List<VendorProfile> findByLocation(String location);
    List<VendorProfile> findByCategoryAndLocation(String category, String location);
    List<VendorProfile> findByIsVerifiedTrue();
}