package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.WeddingDTO;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.WeddingMapper;
import com.weddingplanner.wedding_planner.model.Wedding;
import com.weddingplanner.wedding_planner.repository.WeddingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeddingService {
    private final WeddingRepository weddingRepository;
    private final WeddingMapper weddingMapper;

    @Transactional
    public WeddingDTO createWedding(WeddingDTO weddingDTO, String userId) {
        log.debug("Creating wedding for user: {}", userId);
        Wedding wedding = weddingMapper.toEntity(weddingDTO);
        wedding.setUserId(userId);
        wedding.setCreatedAt(LocalDateTime.now());
        wedding.setUpdatedAt(LocalDateTime.now());

        Wedding savedWedding = weddingRepository.save(wedding);
        log.info("Created wedding with ID: {} for user: {}", savedWedding.getId(), userId);
        return weddingMapper.toDTO(savedWedding);
    }

    @Transactional(readOnly = true)
    public List<WeddingDTO> getUserWeddings(String userId) {
        log.debug("Fetching weddings for user: {}", userId);
        return weddingRepository.findByUserId(userId).stream()
                .map(weddingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WeddingDTO getWeddingById(String id, String userId) {
        log.debug("Fetching wedding with ID: {} for user: {}", id, userId);
        Wedding wedding = weddingRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Wedding", "id", id));
        return weddingMapper.toDTO(wedding);
    }

    @Transactional
    public WeddingDTO updateWedding(String id, WeddingDTO weddingDTO, String userId) {
        log.debug("Updating wedding with ID: {} for user: {}", id, userId);
        Wedding existingWedding = weddingRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Wedding", "id", id));

        Wedding updatedWedding = weddingMapper.toEntity(weddingDTO);
        updatedWedding.setId(existingWedding.getId());
        updatedWedding.setUserId(userId);
        updatedWedding.setCreatedAt(existingWedding.getCreatedAt());
        updatedWedding.setUpdatedAt(LocalDateTime.now());

        Wedding savedWedding = weddingRepository.save(updatedWedding);
        log.info("Updated wedding with ID: {}", savedWedding.getId());
        return weddingMapper.toDTO(savedWedding);
    }

    @Transactional
    public void deleteWedding(String id, String userId) {
        log.debug("Deleting wedding with ID: {} for user: {}", id, userId);
        Wedding wedding = weddingRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new ResourceNotFoundException("Wedding", "id", id));

        weddingRepository.delete(wedding);
        log.info("Deleted wedding with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<WeddingDTO> getUpcomingWeddings(String userId) {
        log.debug("Fetching upcoming weddings for user: {}", userId);
        LocalDateTime now = LocalDateTime.now();
        return weddingRepository.findByUserIdAndWeddingDateAfter(userId, now).stream()
                .map(weddingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<WeddingDTO> getPastWeddings(String userId) {
        log.debug("Fetching past weddings for user: {}", userId);
        LocalDateTime now = LocalDateTime.now();
        return weddingRepository.findByUserIdAndWeddingDateBefore(userId, now).stream()
                .map(weddingMapper::toDTO)
                .collect(Collectors.toList());
    }
}