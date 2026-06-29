package com.moodbloom.services;

import com.moodbloom.models.MoodEntry;
import com.moodbloom.repositories.MoodEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoodEntryService {
    private final MoodEntryRepository moodEntryRepository;

    public MoodEntryService(MoodEntryRepository moodEntryRepository) {
        this.moodEntryRepository = moodEntryRepository;
    }

    public MoodEntry save(MoodEntry entry) {
        return moodEntryRepository.save(entry);
    }

    public List<MoodEntry> findAll() {
        return moodEntryRepository.findAll();
    }
}
