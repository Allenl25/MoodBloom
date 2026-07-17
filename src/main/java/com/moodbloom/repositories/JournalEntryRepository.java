package com.moodbloom.repositories;

import com.moodbloom.models.JournalEntry;
import com.moodbloom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByUser(User user);

    List<JournalEntry> findByUserOrderByEntryDateDesc(User user);

    Optional<JournalEntry> findByUserAndEntryDate(User user, LocalDate entryDate);

}