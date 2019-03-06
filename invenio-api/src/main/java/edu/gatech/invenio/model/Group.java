package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

import java.util.HashSet;
import java.util.Set;

public class Group {
    private @Id String id;
    private String name;
    private Set<String> members = new HashSet<>();

    public Group() {

    }

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
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
}
