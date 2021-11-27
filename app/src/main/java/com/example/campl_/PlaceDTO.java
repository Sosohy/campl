package com.example.campl_;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceDTO implements Serializable {
    @SerializedName("seq")
    int seq;
    @SerializedName("name")
    String name;
    @SerializedName("linkUrl")
    String linkUrl;
    @SerializedName("location")
    String location;
    @SerializedName("menu")
    String menu;
    @SerializedName("operatingTime")
    String operatingTime;
    @SerializedName("pictureUrls")
    String[] pictureUrls;

    public PlaceDTO(int seq, String name, String linkUrl, String location, String menu, String operatingTime, String[] pictureUrls) {
        this.seq = seq;
        this.name = name;
        this.linkUrl = linkUrl;
        this.location = location;
        this.menu = menu;
        this.operatingTime = operatingTime;
        this.pictureUrls = pictureUrls;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getOperatingTime() {
        return operatingTime;
    }

    public void setOperatingTime(String operatingTime) {
        this.operatingTime = operatingTime;
    }

    public String[] getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(String[] pictureUrls) {
        this.pictureUrls = pictureUrls;
    }
}
