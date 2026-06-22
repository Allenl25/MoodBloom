package com.moodbloom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//logout functionality
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpSession;


@Controller
public class HomeController {

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

    @PostMapping("/logout")
    public String logout(HttpSession session){
        //destroys usr current login session
        session.invalidate();

        //send user back to login page after loging out

        return"redirect:/login";
    }
}

