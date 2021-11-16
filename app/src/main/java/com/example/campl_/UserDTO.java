package com.example.campl_;

public class UserDTO {
    private final String nickName;
    private final String pwd;
    private final String userName;
    
    public UserDTO(String nickName, String pwd, String userName) {
        this.nickName = nickName;
        this.pwd = pwd;
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }
    
    public String getPwd() {
        return pwd;
    }

    public String getUserName() {
        return userName;
    }

}
