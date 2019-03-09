package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

public class Post {
    @Id
    private String id;
    private String content;
    private String groupId;
    private String userId;

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

    public String getGroupId() {
        return groupId;
    }

    public String getUserId() {
        return userId;
    }
}
