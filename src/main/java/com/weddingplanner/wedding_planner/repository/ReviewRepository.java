package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByVendorId(String vendorId);
    List<Review> findByUserId(String userId);
    List<Review> findByRatingGreaterThanEqual(Integer rating);

    @Query("{'metadata.verified': true}")
    List<Review> findVerifiedReviews();
}