package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class Like {
    private @Id String id;
    private String postId;
    private String userId;

    public Like(String postId, String userId) {
        this.postId = postId;
        this.userId = userId;
    }

    public String getPostId() {
        return postId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(id, like.id) &&
                Objects.equals(postId, like.postId) &&
                Objects.equals(userId, like.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, postId, userId);
    }
}
