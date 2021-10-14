package com.example.connect;

import android.net.Uri;

public class vidlist {
    String tit;
    String des;
    String uid;
    String url;
    int like ;

    public vidlist() {
    }

    public void setTit(String tit) {
        this.tit = tit;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public vidlist(String tit, String des, String uid, String url, int like) {
        this.tit = tit;
        this.des = des;
        this.uid = uid;
        this.url = url;
        this.like = like;
    }

    public String getTit() {
        return tit;
    }

    public String getDes() {
        return des;
    }

    public String getUid() {
        return uid;
    }

    public String getUrl() {
        return url;
    }

    public int getLike() {
        return like;
    }
}
