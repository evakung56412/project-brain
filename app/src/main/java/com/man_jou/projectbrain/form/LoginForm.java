package com.man_jou.projectbrain.form;

import org.json.JSONObject;

public class LoginForm implements ApiJsonForm {

    private String email;
    private String password;

    public LoginForm() {}

    public LoginForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUrl() {
        return "http://10.0.2.2:8080/brain/login";
    }

    @Override
    public JSONObject getJsonData() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("email", this.getEmail());
            jsonObject.put("password", this.getPassword());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
