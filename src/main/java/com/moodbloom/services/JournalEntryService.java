package com.moodbloom.services;

import com.moodbloom.models.JournalEntry;
import com.moodbloom.models.User;
import com.moodbloom.repositories.JournalEntryRepository;
import com.moodbloom.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JournalEntryService {

    private final JournalEntryRepository journalEntryRepository;
    private final UserRepository userRepository;

    public JournalEntryService(JournalEntryRepository journalEntryRepository, UserRepository userRepository) {

        this.journalEntryRepository = journalEntryRepository;
        this.userRepository = userRepository;
    }

    public void saveEntry(String username, String title, String content) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        JournalEntry entry = new JournalEntry();

        entry.setTitle(title);
        entry.setContent(content);
        entry.setEntryDate(LocalDate.now());
        entry.setUser(user);

        journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getEntriesForUser(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        return journalEntryRepository.findByUserOrderByEntryDateDesc(user);
    }
}