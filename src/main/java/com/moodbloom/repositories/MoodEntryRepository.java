package com.moodbloom.repositories;

import com.moodbloom.models.MoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodEntryRepository extends JpaRepository<MoodEntry, Long>{
}
