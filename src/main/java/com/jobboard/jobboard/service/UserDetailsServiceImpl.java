package com.jobboard.jobboard.service;

import com.jobboard.jobboard.entity.User;
import com.jobboard.jobboard.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            User appUser = user.get();
            List<GrantedAuthority> authoritiesList = List.of(
                    new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name())
            );

            return new org.springframework.security.core.userdetails.User(
                    appUser.getEmail(), appUser.getPassword(), authoritiesList);
        } else {
            throw new UsernameNotFoundException("User not found with email: " + username);

        }
    }
}
