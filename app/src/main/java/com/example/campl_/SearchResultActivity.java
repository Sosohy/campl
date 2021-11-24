package com.example.campl_;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchResultActivity extends AppCompatActivity {

    camplAPI camplAPI;
    ImageButton back;

    RecyclerView searchResult_recycler = null;
    ArrayList<PostDTO> searchPosts = new ArrayList<>();
    RecyclerSearchCardAdapter searchAdapter = new RecyclerSearchCardAdapter(searchPosts);

    TextView pageTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search_result);

        camplAPI = MainActivity.camplAPI;
        pageTitle = (TextView)findViewById(R.id.pageTitle);
        back = (ImageButton)findViewById(R.id.result_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String title = intent.getStringExtra("pageTitle");
        ArrayList<PostDTO> intentPost = (ArrayList<PostDTO>)intent.getSerializableExtra("posts");

        pageTitle.setText(title);
        searchPosts.addAll(intentPost);

        searchResult_recycler = findViewById(R.id.searchResult_recycler);
        searchResult_recycler.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }
}