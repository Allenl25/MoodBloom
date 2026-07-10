package com.moodbloom.repositories;

import com.moodbloom.models.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository
        extends JpaRepository<CommunityPost, Long> {
}