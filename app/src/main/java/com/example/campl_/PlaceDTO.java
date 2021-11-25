package com.example.campl_;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceDTO implements Serializable {
    @SerializedName("imgUrl")
    String imgUrl;
    @SerializedName("name")
    String name;
    @SerializedName("content")
    String content;

    public PlaceDTO(String imgUrl, String name, String content) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.content = content;
    }

    public PlaceDTO(String name, String content) {
        this.imgUrl = "default";
        this.name = name;
        this.content = content;
    }

    public PlaceDTO() {
        this.imgUrl = "default";
        this.name = "default";
        this.content = "default";
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
