package com.weddingplanner.wedding_planner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weddingplanner.wedding_planner.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private String id;

    @NotBlank(message = "Email is required")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String phoneNumber;
    @JsonProperty("role")
    private User.UserRole role;
    private UserProfileDTO profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Data
    public static class UserProfileDTO {
        private String profilePicture;
        private String address;
        private String city;
        private String country;
        private String preferences;
    }
}