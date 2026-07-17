package com.moodbloom.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "community_comments")
public class CommunityComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean anonymous = false;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost communityPost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CommunityComment() {
    }

    public CommunityComment(
            String content,
            boolean anonymous,
            CommunityPost communityPost,
            User user
    ) {
        this.content = content;
        this.anonymous = anonymous;
        this.communityPost = communityPost;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

    public CommunityPost getCommunityPost() {
        return communityPost;
    }

    public void setCommunityPost(CommunityPost communityPost) {
        this.communityPost = communityPost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDisplayName() {
        if (anonymous) {
            return "Anonymous";
        }

        return user != null ? user.getUsername() : "Unknown User";
    }

    @Override
    public String toString() {
        return "CommunityComment{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", anonymous=" + anonymous +
                '}';
    }
}
