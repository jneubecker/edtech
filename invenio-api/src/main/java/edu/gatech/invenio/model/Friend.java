package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

public class Friend {
    private @Id String id;
    private String user;
    private String friend;
    private boolean approved = false;

    public Friend() {

    }

    public Friend(String user, String friend) {
        this.user = user;
        this.friend = friend;
    }

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getFriend() {
        return friend;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }
}
