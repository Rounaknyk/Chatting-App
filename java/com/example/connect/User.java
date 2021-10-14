package com.example.connect;

public class User {
    String name;
    String email;
    String uid;
    String url;

    public User() {
    }

    public User(String name, String email, String uid, String url) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
