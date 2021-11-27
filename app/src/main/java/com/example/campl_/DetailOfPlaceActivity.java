package com.example.campl_;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOfPlaceActivity extends AppCompatActivity {

    CamplAPI camplAPI;
    PlaceDTO place = null;

    RecyclerView detailImage_recycler = null;
    ArrayList<String> imgUrls = new ArrayList<>();
    RecyclerDetailImageAdapter detailImageAdapter = new RecyclerDetailImageAdapter(imgUrls);

    RecyclerView link_recycler = null;
    ArrayList<UrlDTO> urls = new ArrayList<>();
    RecyclerWritingLinkAdapter linkAdapter = new RecyclerWritingLinkAdapter(urls);

    RecyclerView menu_recycler = null;
    ArrayList<String> menuList = new ArrayList<>();
    RecyclerHotplaceMenuAdapter menuListAdapter = new RecyclerHotplaceMenuAdapter(menuList);

    TextView title;
    TextView location;
    TextView time;

    ImageButton bookmark;
    ImageButton back;

    int seq = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_hotplace);

        camplAPI = MainActivity.camplAPI;

        title = (TextView)findViewById(R.id.detailHotplace_title);
        location = (TextView)findViewById(R.id.detailHotplace_location);
        time = (TextView)findViewById(R.id.detailHotplace_time);

        bookmark = (ImageButton)findViewById(R.id.detailHotplace_bookmark);
        back = (ImageButton)findViewById(R.id.detailHotplace_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        seq = intent.getIntExtra("seq", -1);
        getDetailData(seq);

        place = (PlaceDTO) intent.getSerializableExtra("place");
        setInitContent();

        detailImage_recycler = (RecyclerView)findViewById(R.id.hotplaceImg_recycler);
        detailImage_recycler.setAdapter(detailImageAdapter);
        detailImageAdapter.notifyDataSetChanged();

        link_recycler = (RecyclerView)findViewById(R.id.hotplaceLink_recycler);
        link_recycler.setAdapter(linkAdapter);
        linkAdapter.notifyDataSetChanged();

        menu_recycler = (RecyclerView)findViewById(R.id.menu_recycler);
        menu_recycler.setAdapter(menuListAdapter);
        menuListAdapter.notifyDataSetChanged();
    }

    public void getDetailData(int seq){
        camplAPI.getPlaceDTO(seq).enqueue(new Callback<PlaceDTO>() {
            @Override
            public void onResponse(Call<PlaceDTO> call, Response<PlaceDTO> response) {
                if(response.isSuccessful())
                    place = response.body();
            }

            @Override
            public void onFailure(Call<PlaceDTO> call, Throwable t) {

            }
        });
    }

    void setInitContent(){
        title.setText(place.getName());
        location.setText(place.getLocation());
        time.setText(place.getOperatingTime());
        if(place.getPictureUrls() != null)
            Collections.addAll(imgUrls, place.getPictureUrls());
        if(!place.getLinkUrl().equals(""))
            urls.addAll(getUrlDTOs(place.getLinkUrl()));
        if(!place.getMenu().equals(""))
            Collections.addAll(menuList, place.getMenu().split(","));

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
}