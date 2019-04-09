package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Group {
    private @Id String id;
    private String name;
    private Set<String> members = new HashSet<>();
    private Set<String> admins = new HashSet<>();
    private Set<String> moderators = new HashSet<>();

    public Group() {

    }

    public Group(String name, String adminId) {
        this.name = name;
        this.admins.add(adminId);
    }


    public String getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getMembers() {
        return this.members;
    }

    public Set<String> getAdmins() {
        return admins;
    }

    public Set<String> getModerators() {
        return moderators;
    }
}
