package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.Vendor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.DayOfWeek;
import java.util.List;

public interface VendorRepository extends MongoRepository<Vendor, String> {
    Page<Vendor> findByCategory(String category, Pageable pageable);
    Page<Vendor> findByLocationAndCategory(String location, String category, Pageable pageable);
    Page<Vendor> findByRatingGreaterThan(Double rating, Pageable pageable);
}