package com.moodbloom.repositories;

import com.moodbloom.models.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentRepository
        extends JpaRepository<CommunityComment, Long> {
}