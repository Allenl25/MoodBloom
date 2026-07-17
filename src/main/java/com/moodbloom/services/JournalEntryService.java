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

        JournalEntry entry = journalEntryRepository.findByUserAndEntryDate(user, LocalDate.now()).orElse(null);

        if (entry == null) {
            entry = new JournalEntry();
            entry.setUser(user);
            entry.setEntryDate(LocalDate.now());
        }

        entry.setTitle(title);
        entry.setContent(content);

        journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getEntriesForUser(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        return journalEntryRepository.findByUserOrderByEntryDateDesc(user);
    }

    public JournalEntry getEntry(Long id) {

        return journalEntryRepository.findById(id).orElseThrow(() -> new RuntimeException("Entry not found."));
    }

    public JournalEntry getTodaysEntry(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        return journalEntryRepository.findByUserAndEntryDate(user, LocalDate.now()).orElse(null);
    }
}