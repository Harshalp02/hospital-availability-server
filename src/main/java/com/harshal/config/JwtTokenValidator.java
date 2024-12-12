package com.harshal.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);
    private static final String SECRET_KEY = "dsakadhkqadkjakdkfnwsdwwndkflwsldkwksnfkadnkadnkda"; // Same secret key

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");

        // Log the entire Authorization header to debug
        logger.info("Authorization Header: {}", jwt); 

        if (jwt == null) {
            logger.warn("Authorization header is missing.");
        } else if (!jwt.startsWith("Bearer ")) {
            logger.warn("JWT token is malformed. It should start with 'Bearer '.");
        } else {
            jwt = jwt.substring(7); // Remove "Bearer " prefix
            logger.info("Extracted JWT Token: {}", jwt); // Log extracted token
        }

        // Continue processing the filter if JWT is valid
        if (jwt != null && jwt.startsWith("Bearer ")) {
            try {
                // Parse the JWT and extract claims
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String email = claims.getSubject();

                // Extract roles from JWT, assuming roles are stored as a List<String>
                Object rolesObj = claims.get("roles");

                // Check if roles are present and valid
                if (rolesObj instanceof List) {
                    List<String> roles = (List<String>) rolesObj;

                    if (roles != null && !roles.isEmpty()) {
                        // Convert roles to GrantedAuthority
                        List<GrantedAuthority> authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                        // Create an authentication token with the roles
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(email, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        logger.warn("Roles claim is missing or empty.");
                    }
                } else if (rolesObj instanceof String) {
                    // Handle case where roles are stored as a single string (not a list)
                    List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority((String) rolesObj));
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    logger.warn("Roles claim is not in the expected format.");
                }

            } catch (Exception e) {
                logger.error("Invalid JWT Token", e);
                throw new ServletException("Invalid JWT Token", e);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }

}
