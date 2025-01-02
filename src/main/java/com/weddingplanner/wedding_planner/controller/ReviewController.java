package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.ReviewDTO;
import com.weddingplanner.wedding_planner.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewDTO> createReview(
            @Validated @RequestBody ReviewDTO reviewDTO,
            @RequestAttribute String userId) {
        return ResponseEntity.ok(reviewService.createReview(reviewDTO, userId));
    }

    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<List<ReviewDTO>> getVendorReviews(@PathVariable String vendorId) {
        return ResponseEntity.ok(reviewService.getVendorReviews(vendorId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> updateReview(
            @PathVariable String id,
            @Validated @RequestBody ReviewDTO reviewDTO,
            @RequestAttribute String userId) throws BadRequestException {
        return ResponseEntity.ok(reviewService.updateReview(id, reviewDTO, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(
            @PathVariable String id,
            @RequestAttribute String userId) throws BadRequestException {
        reviewService.deleteReview(id, userId);
        return ResponseEntity.noContent().build();
    }
}
