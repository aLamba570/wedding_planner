package com.weddingplanner.wedding_planner.service;

import com.weddingplanner.wedding_planner.dto.AuthenticationResponse;
import com.weddingplanner.wedding_planner.dto.AuthenticationRequest;
import com.weddingplanner.wedding_planner.exception.ResourceNotFoundException;
import com.weddingplanner.wedding_planner.model.User;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import com.weddingplanner.wedding_planner.repository.UserRepository;
import com.weddingplanner.wedding_planner.repository.VendorProfileRepository;
import com.weddingplanner.wedding_planner.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final VendorProfileRepository vendorProfileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", request.getEmail()));

        String token = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // Get vendor profile if user is a vendor
        VendorProfile vendorProfile = null;
        if (user.getRole() == User.UserRole.VENDOR) {
            vendorProfile = vendorProfileRepository.findByUserId(user.getId())
                    .orElse(null);
        }

        return AuthenticationResponse.builder()
                .token(token)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .vendorProfile(vendorProfile)
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}