package com.example.connect;

public class Post {
    String name;
    String title;
    String des;
    String uid;
    String url;
    int like;

    public Post() {
    }

    public Post(String name, String title, String des, String uid, String url, int like) {
        this.name = name;
        this.title = title;
        this.des = des;
        this.uid = uid;
        this.url = url;
        this.like = like;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }
}
