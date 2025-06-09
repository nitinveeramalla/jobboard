package com.jobboard.jobboard.service;

import com.jobboard.jobboard.dto.UserLoginRequest;
import com.jobboard.jobboard.dto.UserRegistrationRequest;
import com.jobboard.jobboard.entity.User;
import com.jobboard.jobboard.repository.UserRepository;
import com.jobboard.jobboard.security.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public String register(UserRegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.info("Email already exists");
            return "Email already Exists";
        }
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        User newUser = new User(request.getName(), request.getEmail(), hashedPassword, request.getRole());
        try {
            userRepository.save(newUser);
            log.info("new User registered");
            return "new User registered";
        } catch (Exception e) {
            log.error("registration unsuccessful with exception" + e);
            return "registration unsuccessful";
        }
    }

    public String login(UserLoginRequest request) {
        Optional<User> user = userRepository.findByEmail(request.getEmail());
        if (user.isPresent() && passwordEncoder.matches(request.getPassword(),
                user.get().getPassword())) {
            return jwtService.generateToken(user.get());
        }
        log.error("Login Failed");
        return "Login Failed";
    }
}
