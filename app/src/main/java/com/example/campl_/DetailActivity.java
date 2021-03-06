package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    CamplAPI camplAPI;
    PostDTO post = null;

    RecyclerView detailImage_recycler = null;
    ArrayList<String> imgUrls = new ArrayList<>();
    RecyclerDetailImageAdapter detailImageAdapter = new RecyclerDetailImageAdapter(imgUrls);

    RecyclerView link_recycler = null;
    ArrayList<UrlDTO> urls = new ArrayList<>();
    RecyclerWritingLinkAdapter linkAdapter = new RecyclerWritingLinkAdapter(urls);

    TextView title;
    TextView timing;
    TextView duration;
    TextView cost;
    TextView content;

    ImageButton like;
    ImageButton bookmark;
    ImageButton back;
    ImageButton more;

    int seq = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail);

        camplAPI = MainActivity.camplAPI;

        title = (TextView)findViewById(R.id.detail_title);
        timing = (TextView)findViewById(R.id.detail_timing);
        duration = (TextView)findViewById(R.id.detail_duration);
        cost = (TextView)findViewById(R.id.detail_cost);
        content = (TextView)findViewById(R.id.detail_content);

        like = (ImageButton)findViewById(R.id.detail_like);
        bookmark = (ImageButton)findViewById(R.id.detail_bookmark);
        back = (ImageButton)findViewById(R.id.detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        more = (ImageButton)findViewById(R.id.more_post);
        more.setOnClickListener(optionClick);

        Intent intent = getIntent();
        seq = intent.getIntExtra("seq", -1);
        post = (PostDTO) intent.getSerializableExtra("post");
        //getDetailData(seq);

        camplAPI.getPost(seq).enqueue(new Callback<PostDTO>() {
            @Override
            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                PostDTO p = new PostDTO();
                if(response.isSuccessful()) {
                    p = (PostDTO) response.body();
                    post = p;
                    Log.e("detail", String.valueOf(response.code()));

                    Log.e("seq", String.valueOf(seq));
                    setInitContent();
                }
            }


            @Override
            public void onFailure(Call<PostDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });


        like.setOnClickListener(likeClick);
        bookmark.setOnClickListener(bookmarkClick);

        detailImage_recycler = findViewById(R.id.detail_recycler);
        detailImage_recycler.setAdapter(detailImageAdapter);
        detailImageAdapter.notifyDataSetChanged();

        link_recycler = (RecyclerView) findViewById(R.id.detail_link_recycler);
        link_recycler.setAdapter(linkAdapter);
        linkAdapter.notifyDataSetChanged();
    }

    public void getDetailData(int seq){
        camplAPI.getPost(seq).enqueue(new Callback<PostDTO>() {
            @Override
            public void onResponse(Call<PostDTO> call, Response<PostDTO> response) {
                if(response.isSuccessful())
                    post = (PostDTO) response.body();
                Log.e("detail", String.valueOf(response.code()));
            }


            @Override
            public void onFailure(Call<PostDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    void setInitContent(){
        title.setText(post.getTitle());
        int tdx =  camplAPI.timingQuery.indexOf(post.getTimingType());
        timing.setText(camplAPI.timingD[tdx]);
        timing.setBackgroundResource(camplAPI.backGData[tdx]);

        duration.setText(camplAPI.durationD[camplAPI.durationQuery.indexOf(post.getDurationTimeType())]);
        cost.setText(camplAPI.costD[camplAPI.costQuery.indexOf(post.getCostType())]);
        content.setText(post.getContent());

        if(post.getPictureUrls() != null)
            Collections.addAll(imgUrls, post.getPictureUrls());

        if(!post.getUrls().equals(""))
            urls.addAll(getUrlDTOs(post.getUrls()));

        if(post.isLike())
            like.setBackgroundResource(R.drawable.like);
        else
           like.setBackgroundResource(R.drawable.like_1);

        if(post.isBookmark())
            bookmark.setBackgroundResource(R.drawable.save);
        else
            bookmark.setBackgroundResource(R.drawable.save_1);
    }

    ArrayList<UrlDTO> getUrlDTOs(String urls){
        ArrayList<UrlDTO> urlDTOS = new ArrayList<>();
        String[] split = urls.split(",");

        for(int i=0; i<split.length; i++){
            String[] url = split[i].split("_");
            urlDTOS.add(new UrlDTO(url[0], url[1]));
        }
        return urlDTOS;
    }


    View.OnClickListener optionClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            PopupMenu popup= new PopupMenu(getApplicationContext(), view);//v??? ????????? ?????? ??????

            getMenuInflater().inflate(R.menu.option_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.modify_post:
                            Intent intent = new Intent(getApplicationContext(), ModifyPostActivity.class);
                            intent.putExtra("post", post);
                            intent.putExtra("seq", seq);
                            startActivity(intent);
                            finish();
                            return true;
                        case R.id.delete_post:
                            camplAPI.deletePost(seq).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if(response.code() == 200)
                                        Toast.makeText(getApplicationContext(), "?????????????????????.", Toast.LENGTH_SHORT).show();
                                    Log.e("response", String.valueOf(response.code()));
                                    finish();
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
                    }
                    return false;
                }
            });
            popup.show();
        }
    };

    View.OnClickListener likeClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(post.isLike()) { //????????? ????????? ??????
                camplAPI.cancelLike(seq).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            post.setLike(false);
                            like.setBackgroundResource(R.drawable.like_1);
                        }
                        Log.e("like", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }else{ //????????? ??? ??????
                camplAPI.likePost(seq).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            post.setLike(true);
                            like.setBackgroundResource(R.drawable.like);
                        }
                        Log.e("bookmark", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }

        }
    };


    View.OnClickListener bookmarkClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(post.isBookmark()) { //????????? ????????? ??????
                camplAPI.cancelBookmark(seq).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            post.setBookmark(false);
                            bookmark.setBackgroundResource(R.drawable.save_1);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }else{ //????????? ??? ??????
                camplAPI.bookmarkPost(seq).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            post.setBookmark(true);
                            bookmark.setBackgroundResource(R.drawable.save);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    };
}