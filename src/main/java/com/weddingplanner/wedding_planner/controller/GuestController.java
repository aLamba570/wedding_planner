package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.GuestDTO;
import com.weddingplanner.wedding_planner.model.Guest;
import com.weddingplanner.wedding_planner.service.GuestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService guestService;

    @PostMapping("/wedding/{weddingId}")
    public ResponseEntity<GuestDTO> addGuest(
            @PathVariable String weddingId,
            @Validated @RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.addGuest(guestDTO, weddingId));
    }

    @PutMapping("/{id}/rsvp")
    public ResponseEntity<GuestDTO> updateRsvp(
            @PathVariable String id,
            @RequestParam Guest.RsvpStatus status) {
        return ResponseEntity.ok(guestService.updateGuestRsvp(id, status));
    }

    @GetMapping("/wedding/{weddingId}")
    public ResponseEntity<List<GuestDTO>> getWeddingGuests(@PathVariable String weddingId) {
        return ResponseEntity.ok(guestService.getWeddingGuests(weddingId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeGuest(@PathVariable String id) {
        guestService.removeGuest(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/table")
    public ResponseEntity<Void> assignTable(
            @PathVariable String id,
            @RequestParam String tableNumber) {
        guestService.assignTableToGuest(id, tableNumber);
        return ResponseEntity.noContent().build();
    }
}