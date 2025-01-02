package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.Wedding;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface WeddingRepository extends MongoRepository<Wedding, String> {
    List<Wedding> findByUserId(String userId);
    Optional<Wedding> findByUserIdAndId(String userId, String id);
    List<Wedding> findByUserIdAndWeddingDateAfter(String userId, LocalDateTime date);
    List<Wedding> findByUserIdAndWeddingDateBefore(String userId, LocalDateTime date);
}