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

    public JournalEntry saveEntry(String username, Long entryId, String title, String content) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        JournalEntry entry;

        if (entryId != null) {

            entry = journalEntryRepository.findById(entryId)
                    .filter(existing -> existing.getUser().getId().equals(user.getId()))
                    .orElseThrow(() -> new RuntimeException("Entry not found."));
        }
        else {

            entry = journalEntryRepository.findByUserAndEntryDateAndDeletedFalse(user, LocalDate.now()).orElse(null);

            if (entry == null) {
                entry = new JournalEntry();
                entry.setUser(user);
                entry.setEntryDate(LocalDate.now());
            }
        }

        entry.setTitle(title);
        entry.setContent(content);

        return journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getEntriesForUser(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        return journalEntryRepository.findByUserAndDeletedFalseOrderByEntryDateDesc(user);
    }

    public JournalEntry getEntry(Long id) {

        return journalEntryRepository.findById(id)
                .filter(entry -> !entry.isDeleted())
                .orElseThrow(() -> new RuntimeException("Entry not found."));
    }

    public JournalEntry getTodaysEntry(String username) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        return journalEntryRepository.findByUserAndEntryDateAndDeletedFalse(user, LocalDate.now()).orElse(null);
    }

    public void deleteEntry(String username, Long entryId) {

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));

        JournalEntry entry = journalEntryRepository.findById(entryId)
                .filter(existing -> existing.getUser().getId().equals(user.getId()))
                .orElseThrow(() -> new RuntimeException("Entry not found."));

        entry.setDeleted(true);

        journalEntryRepository.save(entry);
    }
}