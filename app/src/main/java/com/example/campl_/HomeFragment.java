package com.example.campl_;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    public static Retrofit retrofit;
    public static camplAPI camplAPI;

    RecyclerView popular_recycler = null;
    RecyclerView recommand_recycler = null;
    RecyclerView hotplace_recycler = null;

    ArrayList<PostDTO> popularPosts = new ArrayList<PostDTO>();
    ArrayList<PostDTO> recommandPosts = new ArrayList<PostDTO>();
    ArrayList<PostDTO> hotplacePosts = new ArrayList<PostDTO>();

    RecyclerCardAdapter popularAdapter = new RecyclerCardAdapter(popularPosts);
    RecyclerCardAdapter recommandAdapter = new RecyclerCardAdapter(recommandPosts);
    RecyclerCardAdapter hotplaceAdapter = new RecyclerCardAdapter(hotplacePosts);

    ImageButton searchBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //getHomePostData();

        searchBtn = view.findViewById(R.id.home_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });



        popular_recycler = view.findViewById(R.id.popular_recyclerView);
        recommand_recycler = view.findViewById(R.id.recommand_recyclerView);
        hotplace_recycler = view.findViewById(R.id.hotplace_recyclerView);

        popular_recycler.setAdapter(popularAdapter);
        recommand_recycler.setAdapter(recommandAdapter);
        hotplace_recycler.setAdapter(hotplaceAdapter);

        popularAdapter.notifyDataSetChanged();
        recommandAdapter.notifyDataSetChanged();
        hotplaceAdapter.notifyDataSetChanged();

        return view;
    }

    public void getHomePostData() {
        retrofit = new Retrofit.Builder().baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        camplAPI = retrofit.create(camplAPI.class);

        // 인기글
        camplAPI.getPopularList().enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                if (response.isSuccessful()) {
                    List<PostDTO> list = response.body();
                    popularPosts.clear();
                    popularPosts.addAll(list);
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
            }
        });

        //추천글
        camplAPI.getRecommandList().enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                if (response.isSuccessful()) {
                    List<PostDTO> list = response.body();
                    recommandPosts.clear();
                    recommandPosts.addAll(list);
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
            }
        });

        //핫픟레이스
       /* camplAPI.getHotplaceList().enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                if(response.isSuccessful()){
                    List<PostDTO> list = response.body();
                    hotplacePosts.clear();
                    hotplacePosts.addAll(list);
                }
            }

            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
            }
        });
        */


    }
}