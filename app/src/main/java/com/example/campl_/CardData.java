package com.example.campl_;

import android.graphics.Bitmap;

public class CardData {
    private int seq;
    private String title;
    private String imgUrl;
    private String timing;
    private String duration;
    private String cost;

    public CardData() {
        this.seq = 0;
        this.title = "";
        this.imgUrl = "";
        this.timing = "";
        this.duration = "";
        this.cost = "";
    }

    public CardData(int seq, String title, String imgUrl, String timing, String duration, String cost) {
        this.seq = seq;
        this.title = title;
        this.imgUrl = imgUrl;
        this.timing = timing;
        this.duration = duration;
        this.cost = cost;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getDuration() {
        return duration;
    }

    public String getTiming() {
        return timing;
    }

    public String getTitle() {
        return title;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCost() {
        return cost;
    }
}
