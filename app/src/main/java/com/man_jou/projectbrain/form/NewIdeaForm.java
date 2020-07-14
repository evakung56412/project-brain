package com.man_jou.projectbrain.form;

import org.json.JSONObject;

public class NewIdeaForm implements ApiJsonForm{

    private String username;
    private String title;
    private String context;
    private String content;

    public NewIdeaForm() {}

    public NewIdeaForm(String username, String title, String context, String content) {
        this.username = username;
        this.title = title;
        this.context = context;
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String getUrl() {
        return "http://10.0.2.2:8080/assign/idea";
    }

    @Override
    public JSONObject getJsonData() {

        JSONObject jsonObject = new JSONObject();
        try {;
            jsonObject.put("username", this.getUsername());
            jsonObject.put("title", this.getTitle());
            jsonObject.put("context", this.getContext());
            jsonObject.put("content", this.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
