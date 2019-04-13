package edu.gatech.invenio.model;

public class UserSettings {
    private String feedPrivacy;

    public UserSettings() {

    }

    public UserSettings(String feedPrivacy) {
        this.feedPrivacy = feedPrivacy;
    }

    public String getFeedPrivacy() {
        return feedPrivacy;
    }

    public void setFeedPrivacy(String feedPrivacy) {
        this.feedPrivacy = feedPrivacy;
    }
}
