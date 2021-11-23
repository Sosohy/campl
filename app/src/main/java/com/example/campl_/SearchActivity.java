package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    int durationData = 0;
    ArrayList<String> timingData = new ArrayList<>();
    ArrayList<String> categoryData = new ArrayList<>();
    ArrayList<String> costData = new ArrayList<>();

    String[] timingQuery = {"GOING_TO_SCHOOL", "EMPTY_LECTURE", "DISMISSAL"};
    String[] categoryQuery = {"식당", "술", "디저트", "테마 카페", "문화생활", "방탈출", "운동", "쇼핑"};
    String[] costQuery = {"UNDER1", "BETWEEN1_3", "BETWEEN3_5", "OVER5"};

    String[] timing = {"학교 갈 때", "공강 시간에", "집 가는 길에"};
    String[] category = {"식당", "술", "디저트", "테마 카페", "문화생활", "방탈출", "운동", "쇼핑"};
    String[] cost = {"1만원 이하", "1~3만원", "3~5만원", "5만원 이상"};

    ImageButton back;
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
                EditText start = (EditText)findViewById(R.id.searchTimeStart);
                EditText end = (EditText)findViewById(R.id.searchTimeEnd);

                if((start.getText() != null && start.getText().equals("")) && (end.getText() != null && end.getText().equals(""))){
                    int startT = Integer.parseInt(start.getText().toString());
                    int endT = Integer.parseInt(end.getText().toString());

                    if(endT > startT)
                        durationData = endT - startT;
                    else
                        durationData = (12-startT) + endT;
                }

                Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                intent.putExtra("duration", durationData);
                intent.putExtra("timing", timingData);
                intent.putExtra("category", categoryData);
                intent.putExtra("cost", costData);
                startActivity(intent);
                finish();
            }
        });

        back = (ImageButton)findViewById(R.id.search_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void setSearchBtns(){

        LinearLayout whenLayout = (LinearLayout)findViewById(R.id.search_when);
        LinearLayout categoryLayout = (LinearLayout)findViewById(R.id.search_category);
        LinearLayout costLayout = (LinearLayout)findViewById(R.id.search_cost);

        int[] xmlData = {R.drawable.btn_timing0, R.drawable.btn_timing1, R.drawable.btn_timing2};
        for(int i = 0; i< timing.length; i++){
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv.isSelected()){
                        tv.setSelected(false);
                        timingData.remove(timingQuery[idx]);
                    }else{
                        tv.setSelected(true);
                        timingData.add(timingQuery[idx]);
                    }
                }
            });;
            tv.setBackgroundResource(xmlData[i]);
            tv.setText(timing[i]);
            tv.setPadding(10, 0, 10, 0);
            whenLayout.addView(tv);
        }

        GridLayout gl = new GridLayout(getApplicationContext());
        gl.setColumnCount(4);
        for(int i=0; i<category.length; i++){
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv.isSelected()){
                        tv.setSelected(false);
                        categoryData.remove(categoryQuery[idx]);
                    }else{
                        tv.setSelected(true);
                       categoryData.add(categoryQuery[idx]);
                    }
                }
            });
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(category[i]);
            tv.setPadding(10, 0, 10, 0);
            gl.addView(tv);
        }
        categoryLayout.addView(gl);

        for(int i=0; i<cost.length; i++){
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(tv.isSelected()){
                        tv.setSelected(false);
                        costData.remove(costQuery[idx]);
                    }else{
                        tv.setSelected(true);
                        costData.add(costQuery[idx]);
                    }
                }
            });
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(cost[i]);
            tv.setPadding(10, 0, 10, 0);
            costLayout.addView(tv);
        }
    }
}