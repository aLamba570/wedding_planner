package com.weddingplanner.wedding_planner.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weddingplanner.wedding_planner.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    @JsonProperty(value = "role", required = false)
    private String role;
}