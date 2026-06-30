package com.moodbloom.controllers;

import com.moodbloom.models.User;
import com.moodbloom.services.CustomUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.moodbloom.repositories.UserRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//add error report to user for incorrect information
import org.springframework.ui.Model;
//logout functionality
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "registration";
    }

    @GetMapping("/mood_tracker")
    public String moodTracker() {
        return "mood_tracker";
    }

    @GetMapping("/community")
    public String community() {
        return "community_page";
    }

        //logout user -- Currently handled in SecurityConfig.java
    /*
    @PostMapping("/logout")
    public String logout(HttpSession session){
        //destroys usr current login session
        session.invalidate();

        //send user back to login page after loging out

        return"redirect:/login";
    }
    */
    
    
    @PostMapping("/register")
    public String processRegister(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            @RequestParam(required = false) String guidelines,
            @RequestParam(required = false) String privacy,
            Model model) {

        System.out.println("New registration attempt:");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Password: " + password);
        System.out.println("confirm Password: " + confirmPassword);
        // These keep the user's progress if there is an error.
        
        model.addAttribute("username", username);
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        model.addAttribute("confirmPassword", confirmPassword);

        // Check if username is blank
        if (username == null || username.isBlank()) {
            model.addAttribute("error", "Username is left blank");
            return "registration";
        }

        // Check username length
        // allowed 4 -20 
        if (username.length() < 4 || username.length() > 20) {
            model.addAttribute("error", "Username must be between 4-20 characters.");
            return "registration";
        }

        // Check if email is blank
        if (email == null || email.isBlank()) {
            model.addAttribute("error", "Email is left blank");
            return "registration";
        }

        // Check if password is blank
        if (password == null || password.isBlank()) {
            model.addAttribute("error", "Password is left blank");
            return "registration";
        }

        // Check password length
        if (password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters.");
            return "registration";
        }

        // Check if confirm password is blank
        if (confirmPassword == null || confirmPassword.isBlank()) {
            model.addAttribute("error", "Confirm password is left blank");
            return "registration";
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "registration";
        }

        // Check guidelines checkbox
        if (guidelines == null) {
            model.addAttribute("error", "You must accept the guidelines.");
            return "registration";
        }

        // Check privacy checkbox
        if (privacy == null) {
            model.addAttribute("error", "You must accept the privacy policy.");
            return "registration";
        }

        // Create the new user
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);

        // Encrypt password before saving
        user.setPassword(passwordEncoder.encode(password));

        // Save user to the database
        userRepository.save(user);

        // If the code reaches here, everything passed.
        System.out.println("Registration successful for: " + username);

        return "redirect:/login";

    }

    @GetMapping("/policy")
    public String policy() {
        return "policy";
    }

    @GetMapping("/guidelines")
    public String guidelines() {
        return "guidelines";
    }


 

}