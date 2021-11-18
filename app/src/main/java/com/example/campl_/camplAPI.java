package com.example.campl_;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface camplAPI {
    public static final String BASE_URL = "http://gonggang.yeokeong.com:80/"; // test url  "http://jsonplaceholder.typicode.com/"

    //User Controller
    @POST("/api/v1/users/signup") //("/posts")
    Call<ResponseBody>postSignUp(@Body UserDTO user);

    @POST("/api/v1/users/signin")
    Call<ResponseBody>postSignIn(@Body UserDTO user);

    @POST("/api/v1/users/signout")
    Call<ResponseBody>postSignOut();


    // Post Controller
    @GET("/api/v1/best/posts")
    Call<List<PostDTO>>getPopularList();

    @GET("/api/v1/recommand/posts")
    Call<List<PostDTO>>getRecommandList();

    @GET("/api/v1/bookmark/posts")
    Call<List<PostDTO>>getBookmarkList();

    @GET("/api/v1/posts")
    Call<List<PostDTO>>getPostList(@Query("costType") String cost, @Query("durationTimeType") String duration, @Query("pageSize") int pageSize, @Query("prevLastPostSeq") int prevLastPostSeq, @Query("timingType") String timing);

    @GET("/api/v1/posts/{seq}")
    Call<PostDTO>getPost(@Path("seq") int seq);

    @POST("/api/v1/posts")
    Call<ResponseBody>writingPost(@Body PostDTO post);

    @DELETE("/api/v1/posts/{seq}")
    Call<ResponseBody>deletePost(@Path("seq") int seq);

    @POST("/api/v1/posts/{seq}/bookmark")
    Call<ResponseBody>bookmarkPost(@Path("seq") int seq);

    @DELETE("/api/v1/posts/{seq}/bookmark")
    Call<ResponseBody>cancelBookmark(@Path("seq") int seq);

    @POST("/api/v1/posts/{seq}/like")
    Call<ResponseBody>likePost(@Path("seq") int seq);

    @DELETE("/api/v1/posts/{seq}/like")
    Call<ResponseBody>cancelLike(@Path("seq") int seq);

    //Img Controller
    @POST("/v1/posts/{seq}/pictures")
    Call<ResponseBody>addImage(@Path("seq") int seq);


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
