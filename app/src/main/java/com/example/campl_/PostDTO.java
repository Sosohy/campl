package com.example.campl_;

import com.google.gson.annotations.SerializedName;

public class PostDTO {
    @SerializedName("seq")
    int seq;
    @SerializedName("title")
    String title;
    @SerializedName("user")
    UserDTO user;
    @SerializedName("createdAt")
    String createdAt;
    @SerializedName("isBookmark")
    boolean isBookmark;
    @SerializedName("isLike")
    boolean isLike;
    @SerializedName("likeCnt")
    int likeCnt;
    @SerializedName("pictureUrls")
    String[] pictureUrls;
    @SerializedName("content")
    String content;

    @SerializedName("costType")
    String costType;
    @SerializedName("durationTimeType")
    String durationTimeType;
    @SerializedName("timingType")
    String timingType;

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getSeq() {
        return seq;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLikeCnt(int likeCnt) {
        this.likeCnt = likeCnt;
    }

    public int getLikeCnt() {
        return likeCnt;
    }

    public void setPictureUrls(String[] pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public String[] getPictureUrls() {
        return pictureUrls;
    }

    public void setCostType(String costType) {
        this.costType = costType;
    }

    public String getCostType() {
        return costType;
    }

    public void setDurationTimeType(String durationTimeType) {
        this.durationTimeType = durationTimeType;
    }

    public String getDurationTimeType() {
        return durationTimeType;
    }

    public void setTimingType(String timingType) {
        this.timingType = timingType;
    }

    public String getTimingType() {
        return timingType;
    }
}
