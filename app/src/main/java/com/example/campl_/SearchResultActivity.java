package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultActivity extends AppCompatActivity {

    String searchData[] = new String[4]; //[0] duration [1] timing [2] cost [3] category
    public static Retrofit retrofit;
    public static camplAPI camplAPI;
    ArrayList<PostDTO> searchPosts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        getSearchData();

        retrofit = new Retrofit.Builder().baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        camplAPI = retrofit.create(camplAPI.class);

        camplAPI.getPostList(searchData[2], searchData[0], 0, 0, searchData[1]).enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                if (response.isSuccessful()) {
                    List<PostDTO> list = response.body();
                    searchPosts.clear();
                    searchPosts.addAll(list);
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {

            }
        });
    }


    void getSearchData() {
        Intent intent = getIntent();
        int duration = intent.getIntExtra("duration", -1);
        ArrayList<String> timing = intent.getStringArrayListExtra("timing");
        ArrayList<String> category = intent.getStringArrayListExtra("category");
        ArrayList<String> cost = intent.getStringArrayListExtra("cost");

        switch (duration) {
            case 0:
                searchData[0] = "UNDER1";
                break;
            case 1:
            case 2:
                searchData[0] = "BETWEEN1_2";
                break;
            case 3:
                searchData[0] = "BETWEEN2_3";
                break;
            case 4:
                searchData[0] = "OVER3";
                break;
        }
    }
}