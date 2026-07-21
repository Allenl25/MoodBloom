package com.moodbloom.repositories;

import com.moodbloom.models.CommunityPost;
import com.moodbloom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityPostRepository
        extends JpaRepository<CommunityPost, Long> {

    List<CommunityPost> findAllByOrderByCreatedAtDesc();

    List<CommunityPost> 
    findByTitleContainingIgnoreCaseOrContentContainingIgnoreCaseOrderByCreatedAtDesc(
            String title,
            String content
    );

        List<CommunityPost> findByUserOrderByCreatedAtDesc(User user);

}