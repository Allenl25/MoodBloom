package com.moodbloom.controllers;

import com.moodbloom.models.User;
import com.moodbloom.models.MoodEntry;
import com.moodbloom.repositories.UserRepository;
import com.moodbloom.services.MoodEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.security.Principal;

@RestController
@RequestMapping("/api/mood")
public class MoodEntryController {
    
    private final MoodEntryService moodEntryService;
    private final UserRepository userRepository;

    public MoodEntryController(MoodEntryService moodEntryService, UserRepository userRepository) {
        this.moodEntryService = moodEntryService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public MoodEntry createMoodEntry(@RequestBody MoodEntry entry, Principal principal) {
        String username = principal.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        entry.setUser(user);
        return moodEntryService.save(entry);
    }

    @GetMapping()
    public List<MoodEntry> getAllMoodEntries() {
        return moodEntryService.findAll();
    }
    
}
