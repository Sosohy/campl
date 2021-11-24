package com.example.campl_;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOfPlaceActivity extends AppCompatActivity {

    camplAPI camplAPI;
    PlaceDTO place = null;

    TextView title;
    TextView content;

    ImageButton bookmark;
    ImageButton back;

    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detail_hotplace);

        camplAPI = MainActivity.camplAPI;

        title = (TextView)findViewById(R.id.detailHotplace_content);
        content = (TextView)findViewById(R.id.detailHotplace_content);
        bookmark = (ImageButton)findViewById(R.id.detailHotplace_bookmark);
        back = (ImageButton)findViewById(R.id.detailHotplace_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        getDetailData(name);

        place = new PlaceDTO();
        setInitContent();
    }


    public void getDetailData(String name){
       /* camplAPI.getHotplace(name).enqueue(new Callback<PlaceDTO>() {
            @Override
            public void onResponse(Call<PlaceDTO> call, Response<PlaceDTO> response) {
                if(response.isSuccessful())
                    place = response.body();
            }

            @Override
            public void onFailure(Call<PlaceDTO> call, Throwable t) {
                t.printStackTrace();
            }
        });
        */
    }


    void setInitContent(){
        title.setText(place.getName());
        content.setText(place.getContent());
    }
}