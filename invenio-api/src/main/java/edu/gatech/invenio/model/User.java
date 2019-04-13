package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class User {
    private @Id String id;
    private String name;
    private String password;
    private String displayName;
    private UserSettings userSettings = new UserSettings("private");

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

    public UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
