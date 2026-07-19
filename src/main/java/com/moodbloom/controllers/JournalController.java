package com.moodbloom.controllers;

import com.moodbloom.models.JournalEntry;
import com.moodbloom.services.JournalEntryService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class JournalController {

    private final JournalEntryService journalEntryService;

    public JournalController(JournalEntryService journalEntryService) {
        this.journalEntryService = journalEntryService;
    }

    @GetMapping("/journal")
    public String journal(Authentication authentication, @RequestParam(required = false) Long entryId, Model model) {

        List<JournalEntry> entries = journalEntryService.getEntriesForUser(authentication.getName());

        model.addAttribute("entries", entries);

        JournalEntry currentEntry;

        if (entryId != null) {
            currentEntry = journalEntryService.getEntry(entryId);
        } 
        else {

            currentEntry = journalEntryService.getTodaysEntry(authentication.getName());

            if (currentEntry == null) {
                currentEntry = new JournalEntry();
                currentEntry.setEntryDate(LocalDate.now());
            }
        }

        model.addAttribute("currentEntry", currentEntry);

        return "journal";
    }

    @PostMapping("/journal/save")
    public String saveJournalEntry(@RequestParam(required = false) Long entryId, @RequestParam String title, @RequestParam String content, Authentication authentication) {

        JournalEntry savedEntry = journalEntryService.saveEntry(authentication.getName(), entryId, title, content);

        return "redirect:/journal?entryId=" + savedEntry.getId();
    }

}