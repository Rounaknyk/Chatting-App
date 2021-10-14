package com.example.connect;

public class stories {
    String title;
    String des;
    String uid;
    String url;
    int status;

    public stories() {
    }

    public stories(String title, String des, String uid, String url, int status) {
        this.title = title;
        this.des = des;
        this.uid = uid;
        this.url = url;
        this.status = status;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
