package com.moodbloom.models;


import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community_posts")
public class CommunityPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private boolean anonymous = false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)

    private User user;

     @OneToMany(
            mappedBy = "communityPost",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )

    @OrderBy("createdAt ASC")
    private List<CommunityComment> comments = new ArrayList<>();

    @OneToMany(
        mappedBy = "communityPost",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )

    private List<CommunityReaction> reactions =
        new ArrayList<>();

    public CommunityPost() {
    }

    public CommunityPost(String title, String content, User user, boolean anonymous) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.anonymous = anonymous;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

     public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }

     public List<CommunityComment> getComments() {
        return comments;
    }

    public void setComments(List<CommunityComment> comments) {
        this.comments = comments;
    }

    public List<CommunityReaction> getReactions() {
    return reactions;
    }

    public long getLikeCount() {
        return reactions.stream().filter(reaction ->reaction.getReactionType()== ReactionType.LIKE).count();
    }

    public long getSupportCount() {
        return reactions.stream().filter(reaction ->reaction.getReactionType()== ReactionType.SUPPORT).count();
    }
        //added some for profile page delete button
    public String getDisplayName() {
    if (anonymous) {
        return "Anonymous";
    }

    return user != null
            ? user.getDisplayUsername()
            : "Unknown User";
}
    @Override
    public String toString() {
        return "CommunityPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", createdAt=" + createdAt +
                ", anonymous=" + anonymous +
                ", user=" + (user != null ? user.getUsername() : "null") +
                '}';
    }
}
