package edu.gatech.invenio.model;

public class GroupSettings {
    private String groupPrivacy;

    public GroupSettings() {

    }

    public GroupSettings(String groupPrivacy) {
        this.groupPrivacy = groupPrivacy;
    }

    public String getGroupPrivacy() {
        return groupPrivacy;
    }

    public void setGroupPrivacy(String groupPrivacy) {
        this.groupPrivacy = groupPrivacy;
    }
}
