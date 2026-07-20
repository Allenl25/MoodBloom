package com.moodbloom.controllers;

import com.moodbloom.models.CommunityPost;
import com.moodbloom.models.CommunityReaction;
import com.moodbloom.models.ReactionType;
import com.moodbloom.models.User;
import com.moodbloom.repositories.CommunityPostRepository;
import com.moodbloom.repositories.CommunityReactionRepository;
import com.moodbloom.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityReactionController {

    private final CommunityPostRepository
            communityPostRepository;

    private final CommunityReactionRepository
            communityReactionRepository;

    private final UserRepository userRepository;

    public CommunityReactionController(
            CommunityPostRepository communityPostRepository,
            CommunityReactionRepository communityReactionRepository,
            UserRepository userRepository
    ) {
        this.communityPostRepository =
                communityPostRepository;

        this.communityReactionRepository =
                communityReactionRepository;

        this.userRepository =
                userRepository;
    }

    @PostMapping(
            "/community/posts/{postId}/reactions"
    )
    public String reactToPost(
            @PathVariable Long postId,
            @RequestParam ReactionType reactionType,
            Authentication authentication
    ) {
        CommunityPost post =
                communityPostRepository
                        .findById(postId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Community post was not found"
                                )
                        );

        User user =
                userRepository
                        .findByUsername(
                                authentication.getName()
                        )
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Authenticated user was not found"
                                )
                        );

        CommunityReaction reaction =
                communityReactionRepository
                        .findByCommunityPostAndUser(
                                post,
                                user
                        )
                        .orElse(
                                new CommunityReaction(
                                        reactionType,
                                        post,
                                        user
                                )
                        );

        reaction.setReactionType(reactionType);

        communityReactionRepository.save(reaction);

        return "redirect:/community";
    }
}
