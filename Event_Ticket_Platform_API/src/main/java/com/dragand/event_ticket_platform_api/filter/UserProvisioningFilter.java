package com.dragand.event_ticket_platform_api.filter;

import com.dragand.event_ticket_platform_api.model.User;
import com.dragand.event_ticket_platform_api.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null &&
            authentication.isAuthenticated() &&
            authentication.getPrincipal() instanceof Jwt jwt) {

            createUserIfNotExists(jwt);

        }

        filterChain.doFilter(request, response);
    }

    /**
     * Creates a user in the database with some fields extracted from jwt token if it does not already exist.
     * @param token
     */
    private void createUserIfNotExists(Jwt token) {

        UUID uuid = UUID.fromString(token.getSubject());

        if (userRepository.existsById(uuid)) {
            return;
        }

        Map<String, Object> claims = token.getClaims();

        User user = new User();

        user.setId(uuid);
        user.setName(claims.get("preferred_username").toString());
        user.setEmail(claims.get("email").toString());


        userRepository.save(user);

    }

}
