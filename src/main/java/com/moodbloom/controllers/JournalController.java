package com.moodbloom.controllers;

import com.moodbloom.models.JournalEntry;
import com.moodbloom.services.JournalEntryService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class JournalController {

    private final JournalEntryService journalEntryService;

    public JournalController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping("/journal")
    public String journal(Authentication authentication, Model model) {

        List<JournalEntry> entries = journalEntryService.getEntriesForUser(authentication.getName());

        model.addAttribute("entries", entries);

        return "journal";
    }

    @PostMapping("/journal/save")
    public String saveJournalEntry(@RequestParam String title, @RequestParam String content, Authentication authentication) {

        journalEntryService.saveEntry(authentication.getName(), title, content);

        return "redirect:/journal";
    }

}