package com.moodbloom.repositories;

import com.moodbloom.models.JournalEntry;
import com.moodbloom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {

    List<JournalEntry> findByUser(User user);

    List<JournalEntry> findByUserOrderByEntryDateDesc(User user);

}