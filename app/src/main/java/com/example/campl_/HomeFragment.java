package com.example.campl_;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    CamplAPI camplAPI;

    RecyclerView popular_recycler = null;
    RecyclerView recommand_recycler = null;
    RecyclerView hotplace_recycler = null;

    ArrayList<PostDTO> popularPosts = new ArrayList<PostDTO>();
    ArrayList<PostDTO> recommandPosts = new ArrayList<PostDTO>();
    ArrayList<PlaceDTO> hotplacePosts = new ArrayList<>();

    RecyclerHomeCardAdapter popularAdapter = new RecyclerHomeCardAdapter(popularPosts);
    RecyclerHomeCardAdapter recommandAdapter = new RecyclerHomeCardAdapter(recommandPosts);
    RecyclerHomeHotplaceAdapter hotplaceAdapter = new RecyclerHomeHotplaceAdapter(hotplacePosts);

    ImageButton searchBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        camplAPI = MainActivity.camplAPI;
        popular_recycler = view.findViewById(R.id.popular_recyclerView);
        recommand_recycler = view.findViewById(R.id.recommand_recyclerView);
        hotplace_recycler = view.findViewById(R.id.hotplace_recyclerView);

        popular_recycler.setAdapter(popularAdapter);
        recommand_recycler.setAdapter(recommandAdapter);
        hotplace_recycler.setAdapter(hotplaceAdapter);

        searchBtn = view.findViewById(R.id.home_search);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        popularAdapter.notifyDataSetChanged();
        recommandAdapter.notifyDataSetChanged();
        hotplaceAdapter.notifyDataSetChanged();

        getHomePostData();

        return view;
    }

    public void getHomePostData() {
        camplAPI = MainActivity.camplAPI;

        // 인기글
        camplAPI.getPopularList().enqueue(new Callback<List<PostDTO>>() {
            @Override
            public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                if (response.isSuccessful()) {
                    ArrayList<PostDTO> p = (ArrayList<PostDTO>) response.body();
                    popularAdapter.notifyDataSetChanged();
                }
                Log.e("popular", String.valueOf(response.code()));
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
                    recommandPosts = (ArrayList<PostDTO>) response.body();
                    recommandAdapter.notifyDataSetChanged();
                }
                Log.e("recommand", String.valueOf(response.code()));
            }
            @Override
            public void onFailure(Call<List<PostDTO>> call, Throwable t) {
            }
        });

        //핫플레이스
        camplAPI.getPlaceList().enqueue(new Callback<List<PlaceDTO>>() {
            @Override
            public void onResponse(Call<List<PlaceDTO>> call, Response<List<PlaceDTO>> response) {
                if (response.isSuccessful()) {
                    hotplacePosts = (ArrayList<PlaceDTO>) response.body();
                    hotplaceAdapter.notifyDataSetChanged();
            }
                Log.e("place", String.valueOf(response.code()));
            }

            @Override
            public void onFailure(Call<List<PlaceDTO>> call, Throwable t) {

            }
        });


    }
}