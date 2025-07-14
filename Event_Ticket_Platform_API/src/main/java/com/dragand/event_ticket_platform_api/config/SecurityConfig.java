package com.dragand.event_ticket_platform_api.config;

import com.dragand.event_ticket_platform_api.filter.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserProvisioningFilter userProvisioningFilter
    ) throws Exception {
        http
//                Any request must be authenticated
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
//                Disable CSRF protection as it is not needed in this case
                .csrf(csrf -> csrf.disable())
//                Session management is stateless, meaning no session will be created or used by Spring Security
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
//                OAuth2 Resource Server configuration (Keycloak in this case)
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        Customizer.withDefaults()
                ))
//                Add the UserProvisioningFilter after the BearerTokenAuthenticationFilter
                .addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

        return http.build();

    }

}
