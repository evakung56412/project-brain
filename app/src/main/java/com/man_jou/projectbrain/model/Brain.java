package com.man_jou.projectbrain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class Brain{

    @JsonProperty("id")
    private long id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("ideas")
    private List<Object> ideas;
    @JsonProperty("followers")
    private List<Brain> followers;
    @JsonProperty("todos")
    private List<Object> todos;

    public Brain() {}

    public Brain(long id, String email, String username, String password, String firstName, String lastName, List<Object> ideas, List<Brain> followers, List<Object> todos) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ideas = ideas;
        this.followers = followers;
        this.todos = todos;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public List<Object> getIdeas() {
        return ideas;
    }

    public void setIdeas(List<Object> ideas) {
        this.ideas = ideas;
    }

    public List<Brain> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Brain> followers) {
        this.followers = followers;
    }

    public List<Object> getTodos() {
        return todos;
    }

    public void setTodos(List<Object> todos) {
        this.todos = todos;
    }
}
