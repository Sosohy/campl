package com.example.campl_;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritingFragment extends Fragment {

    camplAPI camplAPI;
    View view;

    ArrayList<String> durationData;
    ArrayList<String> timingData;
    ArrayList<String> categoryData;
    ArrayList<String> costData;

    String[] when = {"학교 갈 때", "공강 시간에", "집 가는 길에"};
    String[] duration = {"1시간 미만", "1~2시간", "2~3시간", "3시간 이상"};
    String[] category = {"식당", "술", "디저트", "테마 카페", "문화생활", "방탈출", "운동", "쇼핑"};
    String[] cost = {"1만원 이하", "1~3만원", "3~5만원", "5만원 이상"};

    EditText title;
    EditText content;
    //이미지 업로드 추가 필요

    Button registerBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = new Intent(getContext(), WritingActivity.class);
        startActivity(intent);
        return inflater.inflate(R.layout.fragment_writing, container, false);

      /*  setInitBtns();

        title = (EditText)view.findViewById(R.id.writing_plan);
        content = (EditText)view.findViewById(R.id.writing_content);

        camplAPI = MainActivity.camplAPI;
        registerBtn = (Button)view.findViewById(R.id.writing_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostDTO post = new PostDTO();
              //  PostDTO post = new PostDTO(title.getText().toString(), new String[]{"test"}, content.getText().toString(), costData.toString(), durationData.toString(), timingData.toString());
                camplAPI.writingPost(post).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(getContext(), "글이 등록되었습니다", Toast.LENGTH_SHORT);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });
        */

      //  return view;
    }
/*
    void setInitBtns(){

        LinearLayout whenLayout = (LinearLayout)view.findViewById(R.id.writing_when);
        LinearLayout durationLayout = (LinearLayout)view.findViewById(R.id.writing_duration);
        LinearLayout categoryLayout = (LinearLayout)view.findViewById(R.id.writing_category);
        LinearLayout costLayout = (LinearLayout)view.findViewById(R.id.writing_cost);


        for(int i=0; i<when.length; i++){
            Button tv = new Button(getContext());
            tv.setText(when[i]);
            whenLayout.addView(tv);
        }

        for(int i=0; i<duration.length; i++){
            Button tv = new Button(getContext());
            tv.setText(duration[i]);
            durationLayout.addView(tv);
        }

        GridLayout gl = new GridLayout(getContext());
        gl.setColumnCount(4);
        for(int i=0; i<category.length; i++){
            Button tv = new Button(getContext());
            tv.setText(category[i]);
            gl.addView(tv);
        }
        categoryLayout.addView(gl);

        for(int i=0; i<cost.length; i++){
            Button tv = new Button(getContext());
            tv.setText(cost[i]);
            costLayout.addView(tv);
        }

    }*/
}