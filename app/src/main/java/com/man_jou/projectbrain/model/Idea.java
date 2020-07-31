package com.man_jou.projectbrain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Idea implements Comparable<Idea>{

    @JsonProperty("id")
    private long id;

    @JsonProperty("citeId")
    private String citeId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("context")
    private String context;

    @JsonProperty("content")
    private String content;

    @JsonProperty("author")
    private Brain author;

    public Idea() {}

    public Idea(long id, String citeId, String title, String context, String content, Brain author) {
        this.id = id;
        this.citeId = citeId;
        this.title = title;
        this.context = context;
        this.content = content;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Brain getAuthor() {
        return author;
    }

    public void setAuthor(Brain author) {
        this.author = author;
    }

    public String getCiteId() {
        return citeId;
    }

    public void setCiteId(String citeId) {
        this.citeId = citeId;
    }

    @Override
    public int compareTo(Idea o) {
        return (int) (this.id - o.id);
    }
}
