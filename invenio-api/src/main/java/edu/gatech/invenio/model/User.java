package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class User {
    private @Id String id;
    private String name;
    private String password;
    private String displayName;
    private Set<String> friends = new HashSet<>();

    public User() {

    }

    public User(String name, String password, String displayName) {
        this.name = name;
        this.password = password;
        this.displayName = displayName;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Set<String> getFriends() {
        return friends;
    }
}
