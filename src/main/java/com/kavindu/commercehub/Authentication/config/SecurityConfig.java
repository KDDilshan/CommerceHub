package com.kavindu.commercehub.Authentication.config;

import com.kavindu.commercehub.Authentication.security.JwtAuthenticationFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class SecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf->csrf.disable())
                .addFilterBefore(new CorsFilter(corsConfigurationSource()),UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(auth-> auth
                        .requestMatchers("/auth/api/**").permitAll()
                        .requestMatchers("/product/v1/delete/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/user/**").hasAnyAuthority("ROLE_USER","ROLE_ADMIN")
                        .requestMatchers(
                                "/product/v1/get/**",
                                "/product/v1/All/**",
                                "/product/v1/orderName",
                                "/product/v1/orderPrice",
                                "/product/v1/serch",
                                "/product/v1/product-image/upload/**",
                                "/product/v1/product-image/**"
                        ).permitAll()
                        .requestMatchers("/product/v1/update/**","/product/v1/create"
                                ).hasAnyAuthority("ROLE_USER")
                )
                .sessionManagement(session->session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000")); // Your frontend URL
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed HTTP methods
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With")); // Allowed headers
        configuration.setAllowCredentials(true); // Allow credentials (cookies or auth headers)
        configuration.setExposedHeaders(List.of("Authorization")); // Expose the Authorization header to frontend

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Apply CORS settings to all routes

        return source;
    }
}