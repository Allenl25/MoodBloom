package com.moodbloom.controllers;

import com.moodbloom.models.MoodEntry;
import com.moodbloom.services.MoodEntryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/mood")
public class MoodEntryController {
    
    private final MoodEntryService moodEntryService;

    public MoodEntryController(MoodEntryService moodEntryService) {
        this.moodEntryService = moodEntryService;
    }

    @PostMapping
    public MoodEntry createMoodEntry(@RequestBody MoodEntry entry) {
        return moodEntryService.save(entry);
    }

    @GetMapping()
    public List<MoodEntry> getAllMoodEntries() {
        return moodEntryService.findAll();
    }
    
}
