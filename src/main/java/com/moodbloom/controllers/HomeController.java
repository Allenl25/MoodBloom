package com.moodbloom.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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


   @GetMapping("/policy")
    public String policy() {
    return "policy";
    }

    @GetMapping("/guidelines")
public String guidelines() {
    return "guidelines";
}
}
 

