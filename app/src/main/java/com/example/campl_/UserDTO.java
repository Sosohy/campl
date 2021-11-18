package com.example.campl_;

public class UserDTO {
    int userSeq;
    String nickName;
    String pwd;
    String userName;
    
    public UserDTO(String nickName, String pwd, String userName) {
        this.nickName = nickName;
        this.pwd = pwd;
        this.userName = userName;
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

}
