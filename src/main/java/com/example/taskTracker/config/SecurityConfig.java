package com.example.taskTracker.config;

import com.example.taskTracker.security.AppReactiveUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppReactiveUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .csrf().disable()
                .httpBasic()
                .and()
                .formLogin().disable()
                .authorizeExchange()
                .pathMatchers("/swagger-ui.html", "/api-docs/**", "/webjars/**", "/v3/**").permitAll()
                .pathMatchers("/users/**").hasAnyRole("USER", "MANAGER")
                .pathMatchers("/tasks", "/tasks/*", "/tasks/*/add-observer/**").hasAnyRole("USER", "MANAGER")
                .pathMatchers("/tasks", "/tasks/*").hasRole("MANAGER") // POST, PUT, DELETE
                .anyExchange().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .build();
    }
}
