package com.example.connect;

public class pending {
    String name;
    String des;
    String uid;
    String url;

    public pending() {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public pending(String name, String des , String uid, String url) {
        this.name = name;
        this.des = des;
        this.uid = uid;
        this.url = url;

    }
}
