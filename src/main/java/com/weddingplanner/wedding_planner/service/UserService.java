package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.UserDTO;
import com.weddingplanner.wedding_planner.dto.AuthenticationResponse;
import com.weddingplanner.wedding_planner.dto.WeddingDTO;
import com.weddingplanner.wedding_planner.exception.ResourceAlreadyExistsException;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.mapper.UserMapper;
import com.weddingplanner.wedding_planner.mapper.WeddingMapper;
import com.weddingplanner.wedding_planner.model.Booking;
import com.weddingplanner.wedding_planner.model.User;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import com.weddingplanner.wedding_planner.model.Wedding;
import com.weddingplanner.wedding_planner.repository.BookingRepository;
import com.weddingplanner.wedding_planner.repository.UserRepository;
import com.weddingplanner.wedding_planner.repository.VendorProfileRepository;
import com.weddingplanner.wedding_planner.repository.WeddingRepository;
import com.weddingplanner.wedding_planner.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final WeddingRepository weddingRepository;  // Add this
    private final BookingRepository bookingRepository;  // Add this
    private final WeddingMapper weddingMapper;
    private final VendorProfileRepository vendorProfileRepository;



    public String getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Transactional(readOnly = true)
    public UserDTO getCurrentUserProfile() {
        String userId = getCurrentUserId();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return userMapper.toDTO(user);
    }


    @Transactional
    public AuthenticationResponse createUser(UserDTO userDTO) {
        System.out.println("Starting user creation process...");
        System.out.println("Received role in DTO: " + userDTO.getRole());

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ResourceAlreadyExistsException("User already exists with email: " + userDTO.getEmail());
        }

        // Create new user
        User user = new User(); // Create user manually instead of using mapper
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());

        // Set role with explicit check
        if (userDTO.getRole() != null && userDTO.getRole().toString().equals("VENDOR")) {
            user.setRole(User.UserRole.VENDOR);
            System.out.println("Setting role as VENDOR");
        } else {
            user.setRole(User.UserRole.USER);
            System.out.println("Setting role as USER");
        }

        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        System.out.println("Saved user with role: " + savedUser.getRole());

        // Generate tokens
        String token = jwtService.generateToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        // Create vendor profile if user is a vendor
        VendorProfile vendorProfile = null;
        if (savedUser.getRole() == User.UserRole.VENDOR) {
            System.out.println("Creating vendor profile...");
            vendorProfile = new VendorProfile();
            vendorProfile.setUserId(savedUser.getId());
            vendorProfile.setBusinessName(savedUser.getFirstName() + " " + savedUser.getLastName());

            // Initialize ContactInfo properly
            VendorProfile.ContactInfo contactInfo = new VendorProfile.ContactInfo();
            contactInfo.setEmail(savedUser.getEmail());
            vendorProfile.setContactInfo(contactInfo);

            // Set initial values for required fields
            vendorProfile.setDescription(""); // Empty description initially
            vendorProfile.setServices(new ArrayList<>()); // Empty services list
            vendorProfile.setCategory(""); // Empty category initially
            vendorProfile.setLocation(""); // Empty location initially
            vendorProfile.setIsVerified(false); // Not verified initially
            vendorProfile.setRating(0.0); // Initial rating

            vendorProfile.setCreatedAt(LocalDateTime.now());
            vendorProfile.setUpdatedAt(LocalDateTime.now());

            vendorProfile = vendorProfileRepository.save(vendorProfile);
            System.out.println("Vendor profile created successfully");
        }

        AuthenticationResponse response = AuthenticationResponse.builder()
                .id(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .role(savedUser.getRole()) // This should now be correctly set
                .token(token)
                .refreshToken(refreshToken)
                .createdAt(savedUser.getCreatedAt())
                .updatedAt(savedUser.getUpdatedAt())
                .vendorProfile(vendorProfile)
                .build();

        System.out.println("Final response role: " + response.getRole());
        return response;
    }

    private VendorProfile createVendorProfile(User user) {
        VendorProfile vendorProfile = new VendorProfile();
        vendorProfile.setUserId(user.getId());
        vendorProfile.setBusinessName(user.getFirstName() + " " + user.getLastName());

        // Initialize ContactInfo
        VendorProfile.ContactInfo contactInfo = new VendorProfile.ContactInfo();
        contactInfo.setEmail(user.getEmail());
        vendorProfile.setContactInfo(contactInfo);

        vendorProfile.setCreatedAt(LocalDateTime.now());
        vendorProfile.setUpdatedAt(LocalDateTime.now());
        return vendorProfileRepository.save(vendorProfile);
    }

    @Transactional(readOnly = true)
    public List<WeddingDTO> getCurrentUserWeddings() {
        String userId = getCurrentUserId();
        List<Wedding> weddings = weddingRepository.findByUserId(userId);
        return weddings.stream()
                .map(weddingMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Booking> getCurrentUserBookings() {
        String userId = getCurrentUserId();
        return bookingRepository.findByUserId(userId)
                .stream()
                .sorted((b1, b2) -> b2.getBookingDate().compareTo(b1.getBookingDate()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO updateUser(String id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userMapper.updateEntity(userDTO, user);
        user.setUpdatedAt(LocalDateTime.now());

        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }
}