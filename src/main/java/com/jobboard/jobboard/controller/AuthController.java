package com.jobboard.jobboard.controller;

import com.jobboard.jobboard.dto.UserLoginRequest;
import com.jobboard.jobboard.dto.UserRegistrationRequest;
import com.jobboard.jobboard.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

        private final AuthService authService;

        public AuthController(AuthService authService) {
            this.authService = authService;
        }

        @PostMapping("/register")
        public String register(@Valid @RequestBody UserRegistrationRequest request) {
            return authService.register(request);
        }

        @PostMapping("/login")
        public String login(@Valid @RequestBody UserLoginRequest request) {
            return authService.login(request);
        }
}
