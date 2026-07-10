package com.moodbloom.controllers;

import com.moodbloom.models.CommunityPost;
import com.moodbloom.models.User;
import com.moodbloom.repositories.CommunityPostRepository;
import com.moodbloom.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommunityPostController {

    private final CommunityPostRepository communityPostRepository;
    private final UserRepository userRepository;

    public CommunityPostController(
            CommunityPostRepository communityPostRepository,
            UserRepository userRepository
    ) {
        this.communityPostRepository = communityPostRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/community/posts")
    public String saveCommunityPost(
            @RequestParam String title,
            @RequestParam String content,
            Authentication authentication
    ) {
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user was not found"
                        )
                );

        CommunityPost communityPost =
                new CommunityPost(title, content, user);

        communityPostRepository.save(communityPost);

        return "redirect:/community";
    }
}