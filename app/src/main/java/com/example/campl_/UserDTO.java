package com.example.campl_;

import java.io.Serializable;

public class UserDTO implements Serializable {
    int userSeq;


    String userImage;
    String nickName;
    String pwd;
    String userName;
    
    UserDTO(String nickName, String pwd, String userName) {
        this.userSeq = -1;
        this.nickName = nickName;
        this.pwd = pwd;
        this.userName = userName;
        this.userImage = "default";
    }

    UserDTO(String nickName, String pwd) {
        this.userSeq = -1;
        this.nickName = nickName;
        this.pwd = pwd;
        this.userName = "user";
        this.userImage = "default";
    }

    UserDTO() {
        this.userSeq = -1;
        this.nickName = "default";
        this.pwd = "default";
        this.userName = "default";
        this.userImage = "default";
    }

    public void setUserSeq(int userSeq) {
        this.userSeq = userSeq;
    }

    public int getUserSeq() {return userSeq;}

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getPwd() {
        return pwd;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }


}
