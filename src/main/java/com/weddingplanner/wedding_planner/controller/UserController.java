package com.weddingplanner.wedding_planner.controller;

import com.weddingplanner.wedding_planner.dto.AuthenticationResponse;
import com.weddingplanner.wedding_planner.dto.RegisterResponse;
import com.weddingplanner.wedding_planner.dto.UserDTO;
import com.weddingplanner.wedding_planner.model.User;
import com.weddingplanner.wedding_planner.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.createUser(userDTO));
    }

}