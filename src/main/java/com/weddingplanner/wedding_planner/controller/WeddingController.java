package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.WeddingDTO;
import com.weddingplanner.wedding_planner.service.WeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weddings")
@RequiredArgsConstructor
public class WeddingController {

    private final WeddingService weddingService;

    private String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @PostMapping
    public ResponseEntity<WeddingDTO> createWedding(@RequestBody WeddingDTO weddingDTO) {
        String userId = getCurrentUserId();
        return ResponseEntity.ok(weddingService.createWedding(weddingDTO, userId));
    }

    @GetMapping
    public ResponseEntity<List<WeddingDTO>> getUserWeddings() {
        String userId = getCurrentUserId();
        return ResponseEntity.ok(weddingService.getUserWeddings(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WeddingDTO> getWeddingById(@PathVariable String id) {
        String userId = getCurrentUserId();
        return ResponseEntity.ok(weddingService.getWeddingById(id, userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WeddingDTO> updateWedding(
            @PathVariable String id,
            @RequestBody WeddingDTO weddingDTO) {
        String userId = getCurrentUserId();
        return ResponseEntity.ok(weddingService.updateWedding(id, weddingDTO, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWedding(@PathVariable String id) {
        String userId = getCurrentUserId();
        weddingService.deleteWedding(id, userId);
        return ResponseEntity.ok().build();
    }
}