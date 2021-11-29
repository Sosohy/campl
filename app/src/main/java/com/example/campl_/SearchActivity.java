package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    CamplAPI camplAPI;
    int durationData = 0;
    ArrayList<Integer> timingData = new ArrayList<>();
    ArrayList<Integer> categoryData = new ArrayList<>();
    ArrayList<Integer> costData = new ArrayList<>();

    String timing = " ";
    String category = " ";
    String cost = " ";

    ImageButton back;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        camplAPI = MainActivity.camplAPI;
        setSearchBtns();
        back = (ImageButton)findViewById(R.id.search_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        submitBtn = (Button)findViewById(R.id.search_submit);
        submitBtn.setOnClickListener(SearchClick);
    }


    View.OnClickListener SearchClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

                if(timingData.size() != 0)
                    timing = camplAPI.timingQuery.get(timingData.get(0));
                if(costData.size() != 0)
                    cost = camplAPI.costQuery.get(costData.get(0));

            ArrayList<String> cData = new ArrayList<>();
            if(categoryData.size() != 0){
                for(int i=0; i<categoryData.size(); i++)
                    cData.add(camplAPI.categoryQuery.get(categoryData.get(i)));
            }else
                cData.add("");

            camplAPI.getPostList(cost, getDurationData(), 10, 50, timing, cData.toArray(new String[cData.size()])).enqueue(new Callback<List<PostDTO>>() {
                @Override
                public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                    ArrayList<PostDTO> searchPosts = new ArrayList<>();
                    if (response.isSuccessful()) {
                        searchPosts = (ArrayList<PostDTO>) response.body();

                        Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                        intent.putExtra("pageTitle", "검색 결과");
                        intent.putExtra("posts", searchPosts);
                        startActivity(intent);
                        finish();
                    }
                    Log.e("search", String.valueOf(response.code()));
                }

                @Override
                public void onFailure(Call<List<PostDTO>> call, Throwable t) {

                }
            });
        }
    };


    String getDurationData(){
        String durationText = "";
        EditText start = (EditText)findViewById(R.id.searchTimeStart);
        EditText end = (EditText)findViewById(R.id.searchTimeEnd);

        if((start.getText() != null && start.length() != 0) && (end.getText() != null && end.length() != 0)){
            int startT = Integer.parseInt(start.getText().toString());
            int endT = Integer.parseInt(end.getText().toString());

            if(endT > startT)
                durationData = endT - startT;
            else
                durationData = (12-startT) + endT;
        }else{
            return "";
        }

        switch (durationData) {
            case 0:
                durationText = "UNDER1";
                break;
            case 1:
            case 2:
                durationText = "BETWEEN1_2";
                break;
            case 3:
                durationText = "BETWEEN2_3";
                break;
            case 4:
                durationText = "OVER3";
                break;
        }

        return durationText;
    }

    void setSearchBtns(){

        LinearLayout whenLayout = (LinearLayout)findViewById(R.id.search_when);
        LinearLayout categoryLayout = (LinearLayout)findViewById(R.id.search_category);
        LinearLayout costLayout = (LinearLayout)findViewById(R.id.search_cost);

        for(int i = 0; i< camplAPI.timingD.length; i++){
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv.isSelected()){
                        tv.setSelected(false);
                        timingData.remove(Integer.valueOf(idx));
                    }else{
                        tv.setSelected(true);
                        timingData.add(idx);
                    }
                }
            });;
            tv.setBackgroundResource(camplAPI.xmlData[i]);
            tv.setText(camplAPI.timingD[i]);
            tv.setTextSize(14);
            tv.setPadding(10, 0, 10, 0);
            whenLayout.addView(tv);
        }

        GridLayout gl = new GridLayout(getApplicationContext());
        gl.setColumnCount(4);
        for(int i=0; i<camplAPI.categoryD.length; i++){
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv.isSelected()){
                        tv.setSelected(false);
                        categoryData.remove(Integer.valueOf(idx));
                    }else{
                        tv.setSelected(true);
                       categoryData.add(Integer.valueOf(idx));
                    }
                }
            });
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(camplAPI.categoryD[i]);
            tv.setTextSize(14);
            tv.setPadding(10, 0, 10, 0);
            gl.addView(tv);
        }
        categoryLayout.addView(gl);

        for(int i=0; i<camplAPI.costD.length; i++){
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv.isSelected()){
                        tv.setSelected(false);
                        costData.remove(Integer.valueOf(idx));
                    }else{
                        tv.setSelected(true);
                        costData.add(idx);
                    }
                }
            });
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(camplAPI.costD[i]);
            tv.setTextSize(14);
            tv.setPadding(10, 0, 10, 0);
            costLayout.addView(tv);
        }
    }
}