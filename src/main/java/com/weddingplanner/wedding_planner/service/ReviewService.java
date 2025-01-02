package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.ReviewDTO;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.ReviewMapper;
import com.weddingplanner.wedding_planner.model.Review;
import com.weddingplanner.wedding_planner.repository.ReviewRepository;
import com.weddingplanner.wedding_planner.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final VendorRepository vendorRepository;
    private final ReviewMapper reviewMapper;

    @Transactional
    public ReviewDTO createReview(ReviewDTO reviewDTO, String userId) {
        // Validate vendor exists
        vendorRepository.findById(reviewDTO.getVendorId())
                .orElseThrow(() -> new ResourceNotFoundException("Vendor", "id", reviewDTO.getVendorId()));

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setUserId(userId);
        review.setReviewDate(LocalDateTime.now());
        review.setCreatedAt(LocalDateTime.now());
        review.setUpdatedAt(LocalDateTime.now());

        // Set metadata
        Review.ReviewMetadata metadata = new Review.ReviewMetadata();
        metadata.setHelpfulVotes(0);
        metadata.setVerified(true);
        metadata.setServiceDate(reviewDTO.getMetadata().getServiceDate());
        review.setMetadata(metadata);

        Review savedReview = reviewRepository.save(review);
        updateVendorRating(reviewDTO.getVendorId());
        return reviewMapper.toDTO(savedReview);
    }

    @Transactional
    public ReviewDTO updateReview(String id, ReviewDTO reviewDTO, String userId) throws BadRequestException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        if (!review.getUserId().equals(userId)) {
            throw new BadRequestException("You can only update your own reviews");
        }

        reviewMapper.updateEntity(reviewDTO, review);
        review.setUpdatedAt(LocalDateTime.now());

        Review updatedReview = reviewRepository.save(review);
        updateVendorRating(review.getVendorId());
        return reviewMapper.toDTO(updatedReview);
    }

    private void updateVendorRating(String vendorId) {
        List<Review> vendorReviews = reviewRepository.findByVendorId(vendorId);
        Double averageRating = vendorReviews.stream()
                .mapToInt(Review::getRating)
                .average()
                .orElse(0.0);

        vendorRepository.findById(vendorId).ifPresent(vendor -> {
            vendor.setRating(averageRating);
            vendor.setUpdatedAt(LocalDateTime.now());
            vendorRepository.save(vendor);
        });
    }

    @Transactional(readOnly = true)
    public List<ReviewDTO> getVendorReviews(String vendorId) {
        return reviewRepository.findByVendorId(vendorId).stream()
                .map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(String id, String userId) throws BadRequestException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review", "id", id));

        if (!review.getUserId().equals(userId)) {
            throw new BadRequestException("You can only delete your own reviews");
        }

        reviewRepository.delete(review);
        updateVendorRating(review.getVendorId());
    }
}
