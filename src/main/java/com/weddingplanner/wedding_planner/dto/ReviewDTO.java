package com.weddingplanner.wedding_planner.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewDTO {
    private String id;
    private String vendorId;
    private String userId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be between 1 and 5")
    @Max(value = 5, message = "Rating must be between 1 and 5")
    private Integer rating;

    @Size(max = 1000, message = "Comment cannot exceed 1000 characters")
    private String comment;

    private LocalDateTime reviewDate;
    private List<String> images;
    private ReviewMetadataDTO metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class ReviewMetadataDTO {
        private Integer helpfulVotes;
        private List<String> tags;
        private Boolean verified;
        private String serviceUsed;
        private LocalDate serviceDate;
    }
}
