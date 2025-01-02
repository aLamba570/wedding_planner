package com.weddingplanner.wedding_planner.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private String vendorId;
    private String userId;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewDate;
    private List<String> images;
    private ReviewMetadata metadata;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class ReviewMetadata {
        private Integer helpfulVotes;
        private List<String> tags;
        private Boolean verified;
        private String serviceUsed;
        private     LocalDate serviceDate;
    }
}
