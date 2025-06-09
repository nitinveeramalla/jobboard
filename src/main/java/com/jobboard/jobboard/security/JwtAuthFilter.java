package com.jobboard.jobboard.security;

import com.jobboard.jobboard.service.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

        private final JwtService jwtService;
        private final UserDetailsServiceImpl userDetailsService;

        public JwtAuthFilter(JwtService jwtService,
                             UserDetailsServiceImpl userDetailsService) {
                this.jwtService = jwtService;
                this.userDetailsService = userDetailsService;
        }

        @Override
        protected void doFilterInternal(HttpServletRequest request,
                                        HttpServletResponse response,
                                        FilterChain chain)
                throws ServletException, IOException {
            // 1) Get header
            String authHeader = request.getHeader("Authorization");
            String jwt = null;
            String email = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                jwt = authHeader.substring(7);
                email = jwtService.extractEmail(jwt);
            }

            // 2) If we got an email and no auth in context yet
            UserDetails userDetails = null;
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                userDetails = userDetailsService.loadUserByUsername(email);

                // 3) Validate token for this user
                if (jwtService.validateToken(jwt, userDetails)) {
                    // 4) Build auth token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );
                    // 5) Put it into context
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }

            // 6) Continue filter chain
            chain.doFilter(request, response);
        }
}
