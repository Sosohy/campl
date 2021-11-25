package com.example.campl_;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface camplAPI {
    public static final String BASE_URL = "http://gonggang.yeokeong.com:80/"; // test url  "http://jsonplaceholder.typicode.com/"

    public static ArrayList<String> timingQuery = new ArrayList(Arrays.asList("GOING_TO_SCHOOL", "EMPTY_LECTURE", "DISMISSAL"));
    public static ArrayList<String> durationQuery = new ArrayList(Arrays.asList("UNDER1", "BETWEEN1_2", "BETWEEN2_3", "OVER3"));
    public static ArrayList<String> categoryQuery = new ArrayList(Arrays.asList("식당", "술", "디저트", "테마 카페", "문화생활", "방탈출", "운동", "쇼핑"));
    public static ArrayList<String> costQuery = new ArrayList(Arrays.asList("UNDER1", "BETWEEN1_3", "BETWEEN3_5", "OVER5"));

    public static String[] timingD = {"학교 갈 때", "공강 시간에", "집 가는 길에"};
    public static String[] durationD = {"1시간 미만", "1~2시간", "2~3시간", "3시간 이상"};
    public static String[] categoryD = {"식당", "술", "디저트", "테마 카페", "문화생활", "방탈출", "운동", "쇼핑"};
    public static String[] costD = {"1만원 이하", "1~3만원", "3~5만원", "5만원 이상"};

    public static int[] xmlData = {R.drawable.btn_timing0, R.drawable.btn_timing1, R.drawable.btn_timing2};
    public static int[] backGData = {R.drawable.go_school, R.drawable.free, R.drawable.go_home};
    public static int[] homeBackGData = {R.drawable.small_go_school, R.drawable.small_free, R.drawable.small_go_home};

    //User Controller
    @POST("api/v1/users/signup") //("/posts")
    Call<ResponseBody>postSignUp(@Body UserDTO user);

    @FormUrlEncoded
    @POST("api/v1/users/signup") //("/posts")
    Call<ResponseBody>postSignUp(@Field("nickName") String userId,
                                 @Field("pwd") String title,
                                 @Field("userName") String text);

    @POST("api/v1/users/signin")
    Call<ResponseBody>postSignIn(@Body UserDTO user);

    @POST("api/v1/users/signout")
    Call<ResponseBody>postSignOut();


    // Post Controller
    @GET("api/v1/best/posts")
    Call<List<PostDTO>>getPopularList();

    @GET("api/v1/recommand/posts")
    Call<List<PostDTO>>getRecommandList();

    @GET("api/v1/bookmark/posts")
    Call<List<PostDTO>>getBookmarkList();

    @GET("api/v1/posts")
    Call<List<PostDTO>>getPostList(@Query("costType") String cost, @Query("durationTimeType") String duration, @Query("pageSize") int pageSize, @Query("prevLastPostSeq") int prevLastPostSeq, @Query("timingType") String timing);

    @GET("api/v1/posts/{seq}")
    Call<PostDTO>getPost(@Path("seq") int seq);

    @POST("api/v1/posts")
    Call<ResponseBody>writingPost(@Body PostDTO post);

    @DELETE("api/v1/posts/{seq}")
    Call<ResponseBody>deletePost(@Path("seq") int seq);

    @POST("api/v1/posts/{seq}/bookmark")
    Call<ResponseBody>bookmarkPost(@Path("seq") int seq);

    @DELETE("api/v1/posts/{seq}/bookmark")
    Call<ResponseBody>cancelBookmark(@Path("seq") int seq);

    @POST("api/v1/posts/{seq}/like")
    Call<ResponseBody>likePost(@Path("seq") int seq);

    @DELETE("api/v1/posts/{seq}/like")
    Call<ResponseBody>cancelLike(@Path("seq") int seq);

    //Img Controller
    @Multipart
    @POST("v1/posts/{seq}/pictures")
    Call<ResponseBody>addImage(@Path("seq") int seq, @Part ArrayList<MultipartBody.Part> imgs);


}
