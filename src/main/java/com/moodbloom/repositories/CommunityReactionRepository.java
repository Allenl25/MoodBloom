package com.moodbloom.repositories;

import com.moodbloom.models.CommunityPost;
import com.moodbloom.models.CommunityReaction;
import com.moodbloom.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityReactionRepository
        extends JpaRepository<CommunityReaction, Long> {

    Optional<CommunityReaction>
    findByCommunityPostAndUser(
            CommunityPost communityPost,
            User user
    );
}
