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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    camplAPI camplAPI;
    int durationData = 0;
    ArrayList<Integer> timingData = new ArrayList<>();
    ArrayList<Integer> categoryData = new ArrayList<>();
    ArrayList<Integer> costData = new ArrayList<>();

    ImageButton back;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        camplAPI = MainActivity.camplAPI;
        setSearchBtns();
        submitBtn = (Button)findViewById(R.id.search_submit);
        submitBtn.setOnClickListener(SearchClick);
    }


    View.OnClickListener SearchClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(timingData.size() == 0 || categoryData.size() == 0 || costData.size() == 0 || timingData.size() == 0)
            {
                Toast.makeText(getApplicationContext(), "검색 조건을 하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            ArrayList<PostDTO> searchPosts = new ArrayList<>();
            //TODO :나중에 카테고리 추가 되면 카테고리 배열로 넣기 -> 함수도 수정
            String[] cData = new String[10];
            for(int i=0; i<categoryData.size(); i++)
                cData[i] = camplAPI.categoryQuery.get(categoryData.get(i));

            camplAPI.getPostList(camplAPI.costQuery.get(costData.get(0)), getDurationData(), 0, 0, camplAPI.timingQuery.get(timingData.get(0))).enqueue(new Callback<List<PostDTO>>() {
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

            Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
            intent.putExtra("pageTitle", "검색 결과");
            intent.putExtra("posts", searchPosts);
            startActivity(intent);
            finish();
        }
    };


    String getDurationData(){
        String durationText = "";
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

        int[] xmlData = {R.drawable.btn_timing0, R.drawable.btn_timing1, R.drawable.btn_timing2};
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
            tv.setPadding(10, 0, 10, 0);
            costLayout.addView(tv);
        }
    }
}