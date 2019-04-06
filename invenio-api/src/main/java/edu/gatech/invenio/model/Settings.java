package edu.gatech.invenio.model;

public class Settings {
    private String feedPrivacy;

    public Settings() {

    }

    public Settings(String feedPrivacy) {
        this.feedPrivacy = feedPrivacy;
    }

    public String getFeedPrivacy() {
        return feedPrivacy;
    }

    public void setFeedPrivacy(String feedPrivacy) {
        this.feedPrivacy = feedPrivacy;
    }
}
