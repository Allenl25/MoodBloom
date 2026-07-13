package com.moodbloom.repositories;

import com.moodbloom.models.CommunityPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository
        extends JpaRepository<CommunityPost, Long> {

    List<CommunityPost> findAllByOrderByCreatedAtDesc();
}