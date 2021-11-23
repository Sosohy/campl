package com.example.campl_;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostDTO {
    @SerializedName("seq")
    int seq;
    @SerializedName("title")
    String title;
    @SerializedName("user")
    UserDTO user;
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
    @SerializedName("category")
    String[] category;

    public PostDTO(int seq, String title, UserDTO user, boolean isBookmark, boolean isLike, int likeCnt, String[] pictureUrls, String content, String costType, String durationTimeType, String timingType, String[] category) {
        this.seq = seq;
        this.title = title;
        this.user = user;
        this.isBookmark = isBookmark;
        this.isLike = isLike;
        this.likeCnt = likeCnt;
        this.pictureUrls = pictureUrls;
        this.content = content;
        this.costType = costType;
        this.durationTimeType = durationTimeType;
        this.timingType = timingType;
        this.category = category;
    }

    public PostDTO(String title, String content, String costType, String durationTimeType, String timingType, String[] category) {
        this.title = title;
        this.content = content;
        this.costType = costType;
        this.durationTimeType = durationTimeType;
        this.timingType = timingType;
        this.category = category;
    }

    public PostDTO() {
        this.seq = 0;
        this.title = "default";
        this.user = new UserDTO();
        this.isBookmark = false;
        this.isLike = false;
        this.likeCnt = 0;
        this.pictureUrls = new String[]{"https://stickershop.line-scdn.net/sticonshop/v1/product/5b5fde61040ab12caa925428/iPhone/main.png", "https://cloudfront-ap-northeast-1.images.arcpublishing.com/chosun/2TKUKXYMQF7ASZEUJLG7L4GM4I.jpg"};
        this.content = "default";
        this.costType = "UNDER1";
        this.durationTimeType = "UNDER1";
        this.timingType = "DISMISSAL";
        this.category = new String[]{"default"};
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

    public String getTitle(){
        return title;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public UserDTO getUser() {
        return user;
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

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }


}
