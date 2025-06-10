package com.jobboard.jobboard.controller;

import com.jobboard.jobboard.dto.UserLoginRequest;
import com.jobboard.jobboard.dto.UserRegistrationRequest;
import com.jobboard.jobboard.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Register and login endpoints")
@RestController @RequestMapping("/api/auth")
public class AuthController {

        private final AuthService authService;

        public AuthController(AuthService authService) {
            this.authService = authService;
        }

        @Operation(summary = "Register a new user",
            description = "Creates a new USER or ADMIN account")
        @PostMapping("/register")
        public String register(@Valid @RequestBody UserRegistrationRequest request) {
            return authService.register(request);
        }

        @Operation(summary = "Login user and return JWT")
        @PostMapping("/login")
        public String login(@Valid @RequestBody UserLoginRequest request) {
            return authService.login(request);
        }
}
