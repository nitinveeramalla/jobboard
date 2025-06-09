package com.jobboard.jobboard.service;

import com.jobboard.jobboard.dto.UserLoginRequest;
import com.jobboard.jobboard.dto.UserRegistrationRequest;
import com.jobboard.jobboard.entity.User;
import com.jobboard.jobboard.repository.UserRepository;
import com.jobboard.jobboard.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return "Email already Exists";
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(request.getName(), request.getEmail(), hashedPassword, request.getRole());
        try {
            userRepository.save(newUser);
            return "new User registered";
        } catch (Exception e) {
            return "registration unsuccessful";
        }
    }

    public String login(UserLoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(),
                user.get().getPassword())) {
            return jwtService.generateToken(user.get());
        }
        return "Login Failed";
    }
}
