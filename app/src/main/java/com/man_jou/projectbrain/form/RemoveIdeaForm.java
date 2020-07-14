package com.man_jou.projectbrain.form;

import org.json.JSONObject;

public class RemoveIdeaForm implements ApiJsonForm{
    private String title;
    private String context;
    private String content;

    public RemoveIdeaForm() {}

    public RemoveIdeaForm(String title, String context, String content) {
        this.title = title;
        this.context = context;
        this.content = content;
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
        return "http://10.0.2.2:8080/idea/remove";
    }

    @Override
    public JSONObject getJsonData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", this.getTitle());
            jsonObject.put("context", this.getContext());
            jsonObject.put("content", this.getContent());

        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
