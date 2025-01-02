package com.weddingplanner.wedding_planner.repository;

import com.weddingplanner.wedding_planner.model.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface GuestRepository extends MongoRepository<Guest, String> {
    List<Guest> findByWeddingId(String weddingId);
    List<Guest> findByRsvpStatus(Guest.RsvpStatus status);
    List<Guest> findByWeddingIdAndRsvpStatus(String weddingId, Guest.RsvpStatus status);

    @Query("{'preferences.dietaryRestrictions': {$exists: true}}")
    List<Guest> findGuestsWithDietaryRestrictions();
}