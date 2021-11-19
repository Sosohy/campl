package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    public static Retrofit retrofit;
    public static camplAPI camplAPI;
    PostDTO post = null;

    TextView title;
    TextView timing;
    TextView duration;
    TextView cost;
    TextView content;

    ImageButton like;
    ImageButton bookmark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        int seq = intent.getIntExtra("seq", -1);
        getDetailData(seq);

        title = (TextView)findViewById(R.id.detail_content);
        timing = (TextView)findViewById(R.id.detail_timing);
        duration = (TextView)findViewById(R.id.detail_duration);
        cost = (TextView)findViewById(R.id.detail_cost);
        content = (TextView)findViewById(R.id.detail_content);

        like = (ImageButton)findViewById(R.id.detail_like);
        bookmark = (ImageButton)findViewById(R.id.detail_bookmark);

        title.setText(post.getTitle());
        timing.setText(post.getTimingType());
        duration.setText(post.getDurationTimeType());
        cost.setText(post.getCostType());
        content.setText(post.getContent());

        if(post.isLike())
            like.setBackgroundResource(R.drawable.ic_launcher_background);
        else
            like.setBackgroundResource(R.drawable.ic_launcher_background);

        if(post.isBookmark())
            bookmark.setBackgroundResource(R.drawable.ic_launcher_background);
        else
            bookmark.setBackgroundResource(R.drawable.ic_launcher_background);

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post.isLike()) { //좋아요 취소일 경우
                    camplAPI.cancelLike(seq).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                like.setBackgroundResource(R.drawable.ic_launcher_background);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }else{ //좋아요 할 경우
                    camplAPI.likePost(seq).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                like.setBackgroundResource(R.drawable.ic_launcher_background);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }

            }
        });


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post.isBookmark()) { //북마크 취소일 경우
                    camplAPI.cancelBookmark(seq).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                bookmark.setBackgroundResource(R.drawable.ic_launcher_background);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }else{ //북마크 할 경우
                    camplAPI.bookmarkPost(seq).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful())
                                bookmark.setBackgroundResource(R.drawable.ic_launcher_background);
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
            }
        });
    }


    public void getDetailData(int seq){
        retrofit = new Retrofit.Builder().baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        camplAPI = retrofit.create(camplAPI.class);

        camplAPI.getPost(seq).enqueue(new Callback<PostDTO>() {
            @Override
            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                if(response.isSuccessful())
                    post = response.body();
            }

            @Override
            public void onFailure(Call<PostDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}