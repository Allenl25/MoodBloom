package com.moodbloom.controllers;

import com.moodbloom.models.User;
import com.moodbloom.services.CustomUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.moodbloom.repositories.UserRepository;
import com.moodbloom.models.CommunityPost;
import com.moodbloom.repositories.CommunityPostRepository;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
//add error report to user for incorrect information
import org.springframework.ui.Model;
//logout functionality
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class HomeController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CommunityPostRepository communityPostRepository;

    public HomeController(UserRepository userRepository, PasswordEncoder passwordEncoder, CommunityPostRepository communityPostRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.communityPostRepository = communityPostRepository;
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
    public String community(Model model) {
        List<CommunityPost> communityPosts =
                communityPostRepository.findAllByOrderByCreatedAtDesc();
        model.addAttribute("communityPosts", communityPosts);
        return "community_page";
    }


    @GetMapping("/policy")
    public String policy() {
        return "policy";
    }

    @GetMapping("/guidelines")
    public String guidelines() {
        return "guidelines";
    }  

        
    
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


        // Check if username already exists in database
        if (userRepository.existsByUsername(username)) {
        model.addAttribute("error", "That username is already taken.");
        return "registration";
        }

        // Check if email is blank
        if (email == null || email.isBlank()) {
            model.addAttribute("error", "Email is left blank");
            return "registration";
        }

        // Check if email format is valid
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            model.addAttribute("error", "Please enter a valid email address.");
            return "registration";
        }

        // Check if email already exists in database
        if (userRepository.existsByEmail(email)) {
        model.addAttribute("error", "That email is already registered.");
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

        //  only allow normal letters, numbers, and common special characters
        if (!password.matches("^[A-Za-z0-9!@#$%^&*()_+=?.,-]+$")) {
        model.addAttribute("error", "Password contains invalid characters.");
        return "registration";
        }

        // Check if password has spaces
        if (password.matches(".*\\s.*")) {
            model.addAttribute("error", "Password cannot contain spaces.");
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

    
 
    

}

 

