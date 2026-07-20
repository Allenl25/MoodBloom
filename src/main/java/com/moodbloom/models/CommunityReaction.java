package com.moodbloom.models;

import jakarta.persistence.*;

@Entity
@Table(
        name = "community_reactions",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"post_id", "user_id"}
                )
        }
)
public class CommunityReaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReactionType reactionType;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private CommunityPost communityPost;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public CommunityReaction() {

    }

    public CommunityReaction(
            ReactionType reactionType,
            CommunityPost communityPost,
            User user
    ) {
        this.reactionType = reactionType;
        this.communityPost = communityPost;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public CommunityPost getCommunityPost() {
        return communityPost;
    }

    public User getUser() {
        return user;
    }
}
