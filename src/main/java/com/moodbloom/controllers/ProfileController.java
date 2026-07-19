package com.moodbloom.controllers;

import java.security.Principal;
import java.util.Optional;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.moodbloom.models.User;
import com.moodbloom.repositories.UserRepository;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {

        String username = principal.getName();
       

        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return "redirect:/login";
        }

        User user = optionalUser.get();

        //negative 1 for testing until we actually do these things
        model.addAttribute("email", user.getEmail());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("dateJoined", user.getDateJoined());
        model.addAttribute("postTotal", -1);
        model.addAttribute("journalTotal", -1);
        model.addAttribute("moodTotal",  -1);
        

        return "profile";
    }

    @PostMapping("/profile")
public String updateProfile(
        @RequestParam String username,
        @RequestParam String email,
        @RequestParam(required = false) String password,
        @RequestParam(required = false) String confirmPassword,
        Model model,
        Principal principal,
        RedirectAttributes redirectAttributes   ) {
            System.out.println("PROFILE POST METHOD WAS HIT");
    if (principal == null) {
        return "redirect:/login";
    }

    String currentUsername = principal.getName();

    Optional<User> optionalUser = userRepository.findByUsername(currentUsername);

    if (optionalUser.isEmpty()) {
        return "redirect:/login";
    }

    User user = optionalUser.get();

    System.out.println("Profile update attempt for: " + user.getUsername());
    System.out.println("New username typed: " + username);
    System.out.println("New email typed: " + email);

      model.addAttribute("email", user.getEmail());
        model.addAttribute("username", user.getUsername());
        model.addAttribute("dateJoined", user.getDateJoined());
        model.addAttribute("postTotal", -1);
        model.addAttribute("journalTotal", -1);
        model.addAttribute("moodTotal",  -1);

        // Check if username is blank
        if (username == null || username.isBlank()) {
            model.addAttribute("error", "Username is left blank");
            return "profile";
        }

        // Check username length
        // allowed 4 -20 
        if (username.length() < 4 || username.length() > 20) {
            model.addAttribute("error", "Username must be between 4-20 characters.");
            return "profile";
        }


        // Check if username already exists in database
        
        if (userRepository.existsByUsername(username) && !username.equals(user.getUsername())) {
        model.addAttribute("error", "That username is already taken.");
        return "profile";
        }

        // Check if email is blank
        if (email == null || email.isBlank()) {
            model.addAttribute("error", "Email is left blank");
            return "profile";
        }

        // Check if email format is valid
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            model.addAttribute("error", "Please enter a valid email address.");
            return "profile";
        }

        // Check if email already exists in database
        if (userRepository.existsByEmail(email)&& !email.equals(user.getEmail())) {
        model.addAttribute("error", "That email is already registered.");
        return "profile";
        }


        if (password.length() != 0){
        if(password.equals(user.getPassword())){
            model.addAttribute("error", "Cannot use a password previous used");
            return "profile";
            }
        
        

        // Check password length
        if (password.length() < 8) {
            model.addAttribute("error", "Password must be at least 8 characters.");
            return "profile";
        }

        //  only allow normal letters, numbers, and common special characters
        if (!password.matches("^[A-Za-z0-9!@#$%^&*()_+=?.,-]+$")) {
        model.addAttribute("error", "Password contains invalid characters.");
        return "profile";
        }

        // Check if password has spaces
        if (password.matches(".*\\s.*")) {
            model.addAttribute("error", "Password cannot contain spaces.");
        return "profile";
        }

        // Check if confirm password is blank
        if (confirmPassword == null || confirmPassword.isBlank()) {
            model.addAttribute("error", "Confirm password is left blank");
            return "profile";
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match.");
            return "profile";
        }
        
    }
        //pass validation and updates profile
        String oldUsername = user.getUsername();
        
        user.setUsername(username);
        user.setEmail(email);

        if (password != null && !password.isBlank()) {
            user.setPassword(passwordEncoder.encode(password));
        }


        if (!oldUsername.equals(username)) {
            return "redirect:/login";
            }


        userRepository.save(user);

        //success message
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully.");

        return "redirect:/profile";
}
@PostMapping("/profile/delete")
public String deleteAccount(
        Principal principal,
        HttpSession session
) {
    if (principal == null) {
        return "redirect:/login";
    }

    String username = principal.getName();

    User user = userRepository.findByUsername(username)
            .orElseThrow(() ->
                    new RuntimeException("User not found")
            );

    // Soft delete the account
    user.setActive(false);
    userRepository.save(user);

    // Log the user out
    session.invalidate();

    return "redirect:/login";
}

}



    