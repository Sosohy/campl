package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    int durationData;
    ArrayList<String> timingData;
    ArrayList<String> categoryData;
    ArrayList<String> costData;
    String[] when = {"학교 갈 때", "공강 시간에", "집 가는 길에"};
    String[] category = {"식당", "술", "디저트", "테마 카페", "문화생활", "방탈출", "운동", "쇼핑"};
    String[] cost = {"1만원 이하", "1~3만원", "3~5만원", "5만원 이상"};

    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        setSearchBtns();
        submitBtn = (Button)findViewById(R.id.search_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
               // intent.putExtra("duration", durationData);
               // intent.putExtra("timing", timingData);
              //  intent.putExtra("category", categoryData);
              //  intent.putExtra("cost", costData);
                startActivity(intent);
                finish();
            }
        });




    }

    void setSearchBtns(){

        LinearLayout whenLayout = (LinearLayout)findViewById(R.id.search_when);
        LinearLayout categoryLayout = (LinearLayout)findViewById(R.id.search_category);
        LinearLayout costLayout = (LinearLayout)findViewById(R.id.search_cost);

        for(int i=0; i<when.length; i++){
            Button tv = new Button(getApplicationContext());
            tv.setText(when[i]);
            whenLayout.addView(tv);
        }

        GridLayout gl = new GridLayout(getApplicationContext());
        gl.setColumnCount(4);
        for(int i=0; i<category.length; i++){
            Button tv = new Button(getApplicationContext());
            tv.setText(category[i]);
            gl.addView(tv);
        }
        categoryLayout.addView(gl);

        for(int i=0; i<cost.length; i++){
            Button tv = new Button(getApplicationContext());
            tv.setText(cost[i]);
            costLayout.addView(tv);
        }

    }
}