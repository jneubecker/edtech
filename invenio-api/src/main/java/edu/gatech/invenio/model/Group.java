package edu.gatech.invenio.model;

import org.springframework.data.annotation.Id;

public class Group {
    private @Id String id;
    private String name;

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
}
