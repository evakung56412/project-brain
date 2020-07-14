package com.man_jou.projectbrain.form;

import org.json.JSONObject;

public class UpdateProfileForm implements ApiJsonForm{

    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public UpdateProfileForm() {}

    public UpdateProfileForm( String username, String password, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getUrl() {
        return "http://10.0.2.2:8080/brain/update";
    }

    @Override
    public JSONObject getJsonData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username", this.getUsername());
            jsonObject.put("password", this.getPassword());
            jsonObject.put("firstName", this.getFirstName());
            jsonObject.put("lastName", this.getLastName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
