package com.example.campl_;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface camplAPI {
    public static final String BASE_URL = "http://gonggang.yeokeong.com:80/"; // test url  "http://jsonplaceholder.typicode.com/"
    @FormUrlEncoded
    @POST("/api/v1/users/signup") //("/posts")
    Call<ResponseBody>postSignUp(@FieldMap HashMap<String, Object> param);

    @POST("/api/v1/users/signin")
    Call<ResponseBody>postSignIn(@Query("pwd") String pwd, @Query("userName") String userName);

    @POST("/api/v1/users/signout")
    Call<ResponseBody>postSignOut();

}

/*
class UserInfo{
    @SerializedName("nickName")
    private String nickName;
    @SerializedName("pwd")
    private String pwd;
    @SerializedName("userName")
    private String userName;

    UserInfo(String nickname, String pwd, String userName){
        this.nickName = nickname;
        this.pwd = pwd;
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

*/
