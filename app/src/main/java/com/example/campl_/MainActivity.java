package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.material.tabs.TabLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static int user = -1;
    public static Retrofit retrofit;
    public static camplAPI camplAPI;

    Button signUpBtn;
    Fragment homeFrag, writingFrag, myPageFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        camplAPI = retrofit.create(camplAPI.class);

        homeFrag = new HomeFragment();
        writingFrag = new WritingFragment();
        myPageFrag = new MyPageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, homeFrag).commit();

        TabLayout tabs = (TabLayout)findViewById(R.id.tabLayout);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                Fragment selected = null;
                if(position == 0){
                    selected = homeFrag;
                }else if (position == 1){
                    selected = writingFrag;
                }else if (position == 2){
                    selected = myPageFrag;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    public static int getUser() {
        return user;
    }

    public static void setUser(int user) {
        MainActivity.user = user;
    }
}