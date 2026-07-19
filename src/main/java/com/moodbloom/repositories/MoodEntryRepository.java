package com.moodbloom.repositories;

import com.moodbloom.models.MoodEntry;
import com.moodbloom.models.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long>{
    List<MoodEntry> findByUserAndDeletedFalseOrderByCreatedAtDesc(User user);
}
