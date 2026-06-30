package com.moodbloom.config;

import com.moodbloom.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

            .userDetailsService(customUserDetailsService)

            .authorizeHttpRequests(auth -> auth

                // Pages that require no login
                .requestMatchers(
                        "/",
                        "/login",
                        "/register",
                        "/policy",
                        "/guidelines",
                        "/community",
                        "/style.css",
                        "/journal.css",
                        "/images/**",
                        "/css/**",
                        "/js/**"
                ).permitAll()

                .anyRequest().authenticated()
            )

            .formLogin(login -> login
                    .loginPage("/login")

                    .usernameParameter("username")
                    .passwordParameter("password")

                    // Page user is sent to after login
                    .defaultSuccessUrl("/", true)
                    .permitAll()
            )

            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
                    .permitAll()
            )

            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    //Used by registration to encode passwords
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}