package com.moodbloom.controllers;
import com.moodbloom.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/users")

public class UserApiController {
     @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {

        //print message in terminal so API is known to be reached
        System.out.println("New API Registration attempt: ");



        //checks if user object is missing anything

        if (user == null){
            return ResponseEntity.badRequest().body("User not found");
        }
            //Print username/email in terminal for testing

            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
        


             // Check if username is blank or missing

        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return ResponseEntity.badRequest().body("Username textbox was left blank");
        }

         // Check if username is too short or too long
        if (user.getUsername().length() < 4 || user.getUsername().length() > 20) {
            return ResponseEntity.badRequest().body("username must be between 4 and 20 characters");
        }

        // Check if email is blank or missing
        if (user.getEmail() == null || user.getEmail().isBlank()) {
           return ResponseEntity.badRequest().body("Email was left blank");
        }

        // Check if password is blank or missing
        if (user.getPassword() == null || user.getPassword().isBlank()) {
           return ResponseEntity.badRequest().body("Password was left blank");
        }

        // Check if password is at least 8 characters
        if (user.getPassword().length() < 8) {
           return ResponseEntity.badRequest().body("password must be at least 8 characters");
        }

        // Check if confirm password is blank or missing
        if (user.getConfirmPassword() == null || user.getConfirmPassword().isBlank()) {
           return ResponseEntity.badRequest().body("confirm password textbox is left blank");
        }

        // Check if password and confirm password match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
           return ResponseEntity.badRequest().body("passwords don't match.");
        }

        // Check if the user accepted the guidelines
        if (user.getGuidelines() == null || user.getGuidelines() == false) {
             return ResponseEntity.badRequest().body("You must accept the guidelines.");
        }

        // Check if the user accepted the privacy policy
        if (user.getPrivacy() == null || user.getPrivacy() == false) {
             return ResponseEntity.badRequest().body("You must accept the privacy policy.");
        }

        // If we reach this point, all validation checks passed
        System.out.println("API registration successful for: " + user.getUsername());

        // Return success response as JSON
        return ResponseEntity.ok().body("User registration successful: " + "User: " + user.getUsername() + "Email: " + user.getEmail());
    }
}


