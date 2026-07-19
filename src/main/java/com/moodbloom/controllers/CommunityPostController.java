package com.moodbloom.controllers;

import com.moodbloom.models.CommunityComment;
import com.moodbloom.models.CommunityPost;
import com.moodbloom.models.User;
import com.moodbloom.repositories.CommunityCommentRepository;
import com.moodbloom.repositories.CommunityPostRepository;
import com.moodbloom.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityPostController {

    private final CommunityPostRepository communityPostRepository;
    private final CommunityCommentRepository communityCommentRepository;
    private final UserRepository userRepository;

    public CommunityPostController(
            CommunityPostRepository communityPostRepository,
            CommunityCommentRepository communityCommentRepository,
            UserRepository userRepository
    ) {
        this.communityPostRepository = communityPostRepository;
        this.communityCommentRepository = communityCommentRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/community/posts")
    public String saveCommunityPost(
            @RequestParam String title,
            @RequestParam String content,
            @RequestParam(
                    name = "anonymous",
                    defaultValue = "false"
            ) boolean anonymous,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);

        CommunityPost communityPost = new CommunityPost(title.trim(),content.trim(),user, anonymous);

        communityPostRepository.save(communityPost);

        return "redirect:/community";
    }

    @PostMapping("/community/posts/{postId}/comments")
    public String saveCommunityComment(
            @PathVariable Long postId,
            @RequestParam String content,
            @RequestParam(
                    name = "anonymous",
                    defaultValue = "false"
            ) boolean anonymous,
            Authentication authentication
    ) {
        User user = getAuthenticatedUser(authentication);

        CommunityPost communityPost =
                communityPostRepository.findById(postId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Community post was not found"
                                )
                        );

        CommunityComment communityComment =
                new CommunityComment(
                        content.trim(),
                        anonymous,
                        communityPost,
                        user
                );

        communityCommentRepository.save(communityComment);

        return "redirect:/community";
    }

    private User getAuthenticatedUser(
            Authentication authentication
    ) {
        if (authentication == null ||
                !authentication.isAuthenticated()) {
            throw new IllegalStateException(
                    "A user must be signed in"
            );
        }

        String username = authentication.getName();

        // System.out.println("AUTHENTICATED NAME: " + username);

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user was not found"
                        )
                );
    }
}