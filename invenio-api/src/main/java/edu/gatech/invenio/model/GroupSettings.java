package edu.gatech.invenio.model;

public class GroupSettings {
    private String groupPrivacy;
    private String memberApprovalPolicy = "admin";

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

    public String getMemberApprovalPolicy() {
        return memberApprovalPolicy;
    }

    public void setMemberApprovalPolicy(String memberApprovalPolicy) {
        this.memberApprovalPolicy = memberApprovalPolicy;
    }
}
