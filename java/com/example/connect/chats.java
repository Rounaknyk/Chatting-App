package com.example.connect;

public class chats {
    String text;
    String name;
    String uid;
    String time;

    public chats(String text, String name, String uid, String time) {
        this.text = text;
        this.name = name;
        this.uid = uid;
        this.time = time;
    }

    public chats() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
