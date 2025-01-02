package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.GuestDTO;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.GuestMapper;
import com.weddingplanner.wedding_planner.model.Guest;
import com.weddingplanner.wedding_planner.repository.GuestRepository;
import com.weddingplanner.wedding_planner.repository.WeddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;
    private final WeddingRepository weddingRepository;
    private final GuestMapper guestMapper;

    @Transactional
    public GuestDTO addGuest(GuestDTO guestDTO, String weddingId) {
        // Validate wedding exists
        weddingRepository.findById(weddingId)
                .orElseThrow(() -> new ResourceNotFoundException("Wedding", "id", weddingId));

        Guest guest = guestMapper.toEntity(guestDTO);
        guest.setWeddingId(weddingId);
        guest.setRsvpStatus(Guest.RsvpStatus.PENDING);
        guest.setCreatedAt(LocalDateTime.now());
        guest.setUpdatedAt(LocalDateTime.now());

        Guest savedGuest = guestRepository.save(guest);
        return guestMapper.toDTO(savedGuest);
    }

    @Transactional
    public GuestDTO updateGuestRsvp(String id, Guest.RsvpStatus rsvpStatus) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));

        guest.setRsvpStatus(rsvpStatus);
        guest.setUpdatedAt(LocalDateTime.now());

        Guest updatedGuest = guestRepository.save(guest);
        return guestMapper.toDTO(updatedGuest);
    }

    @Transactional
    public GuestDTO updateGuestPreferences(String id, Guest.GuestPreferences preferences) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));

        guest.setPreferences(preferences);
        guest.setUpdatedAt(LocalDateTime.now());

        Guest updatedGuest = guestRepository.save(guest);
        return guestMapper.toDTO(updatedGuest);
    }

    @Transactional(readOnly = true)
    public List<GuestDTO> getWeddingGuests(String weddingId) {
        return guestRepository.findByWeddingId(weddingId).stream()
                .map(guestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<GuestDTO> getGuestsByRsvpStatus(String weddingId, Guest.RsvpStatus status) {
        return guestRepository.findByWeddingIdAndRsvpStatus(weddingId, status).stream()
                .map(guestMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeGuest(String id) {
        if (!guestRepository.existsById(id)) {
            throw new ResourceNotFoundException("Guest", "id", id);
        }
        guestRepository.deleteById(id);
    }

    @Transactional
    public void assignTableToGuest(String id, String tableNumber) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest", "id", id));

        guest.setTableAssignment(tableNumber);
        guest.setUpdatedAt(LocalDateTime.now());

        guestRepository.save(guest);
    }
}