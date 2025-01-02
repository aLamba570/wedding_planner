package com.weddingplanner.wedding_planner.dto;

import com.weddingplanner.wedding_planner.model.User;
import com.weddingplanner.wedding_planner.model.Vendor;
import com.weddingplanner.wedding_planner.model.VendorProfile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;
    private String refreshToken;
    private String email;
    private String id;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private User.UserRole role; // Added role
    private VendorProfile vendorProfile;
}