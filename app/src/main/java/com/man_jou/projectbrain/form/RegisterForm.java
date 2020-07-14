package com.man_jou.projectbrain.form;

import org.json.JSONObject;

public class RegisterForm implements ApiJsonForm {

    private String email;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public RegisterForm() {}

    public RegisterForm(String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.firstName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        return "http://10.0.2.2:8080/brain/register";
    }

    @Override
    public JSONObject getJsonData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", this.getEmail());
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
