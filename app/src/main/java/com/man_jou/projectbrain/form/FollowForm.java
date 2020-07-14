package com.man_jou.projectbrain.form;

import org.json.JSONObject;

public class FollowForm implements ApiJsonForm{

    private String followerUsername;
    private String followedUsername;

    public FollowForm() {}

    public FollowForm(String followerUsername, String followedUsername) {
        this.followerUsername = followerUsername;
        this.followedUsername = followedUsername;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }

    public String getFollowedUsername() {
        return followedUsername;
    }

    public void setFollowedUsername(String followedUsername) {
        this.followedUsername = followedUsername;
    }

    @Override
    public String getUrl() {
        return "http://10.0.2.2:8080/brain/follow";
    }

    @Override
    public JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("followerUsername", this.getFollowerUsername());
            jsonObject.put("followedUsername", this.getFollowedUsername());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
