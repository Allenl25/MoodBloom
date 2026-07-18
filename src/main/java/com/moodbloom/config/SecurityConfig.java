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
                        "/resources",
                        "/style.css",
                        "/journal.css",
                        "/policy.css",
                        "/resources.css",
                        "/profile",
                        "/profile.css",
                        "/well_pop",
                        "/well_pop.css",
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


                //wellness tips after sucessful login
        .successHandler((request, response, authentication) -> {

            String[] tips = {
                    "Take five slow, deep breaths when you feel overwhelmed.",
                    "Write down one thing you are grateful for today.",
                    "Spend a few minutes outside or near natural light.",
                    "Remember to drink water throughout the day.",
                    "Break large tasks into smaller, manageable steps.",
                    "Take a short screen break to rest your mind and eyes.",
                    "Try to keep a consistent sleep and wake schedule.",
                    "Talk to someone you trust when you are struggling.",
                    "Celebrate small accomplishments and progress.",
                    "Exercise can help grow your mood.",
                    "Speak to yourself with patience and kindness.",
                    "Focus on what you can control right now.",
                    "Make time for a hobby that helps you relax.",
                    "Avoid comparing your progress to other people.",
                    "Notice your emotions without judging yourself.",
                    "Tidy one small area to create a calmer environment.",
                    "Set healthy boundaries when you need time to recharge.",
                    "Eat regular meals to support your energy and mood.",
                    "Pause and notice what you can see, hear, and feel.",
                    "Ask for support when your feelings become difficult to manage."
};
          

            int randomNumber = (int) (Math.random() * tips.length);
            String randomTip = tips[randomNumber];

            request.getSession().setAttribute("loginTip", randomTip);
                //redirect login user to mood_tracker after login tip
            response.sendRedirect(
                    request.getContextPath() + "/mood_tracker"
            );
        })

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