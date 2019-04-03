package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

public class Post {
    @Id
    private String id;
    private String content;
    private String groupId;
    private String userId;
    private @DBRef List<Post> subPosts = new ArrayList<>();

    public Post() {

    }

    public Post(String content, String groupId, String userId) {
        this.content = content;
        this.groupId = groupId;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getUserId() {
        return userId;
    }

    public List<Post> getSubPosts() {
        return subPosts;
    }
}
