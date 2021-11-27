package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {

    CamplAPI camplAPI;
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
        searchPosts.clear();
        searchPosts.addAll(intentPost);

        searchResult_recycler = findViewById(R.id.searchResult_recycler);
        searchResult_recycler.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();

    }
}