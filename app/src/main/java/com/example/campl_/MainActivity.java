package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TabHost;

import com.google.android.material.tabs.TabLayout;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static int user = -1;
    public static UserDTO userDTO;
    public static Retrofit retrofit;
    public static camplAPI camplAPI;

    TabLayout tabs;
    Fragment selected;


    Button signUpBtn;
    Fragment homeFrag, writingFrag, myPageFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder().baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        camplAPI = retrofit.create(camplAPI.class);

        tabs = (TabLayout)findViewById(R.id.tabLayout);
        homeFrag = new HomeFragment();
        writingFrag = new WritingFragment();
        myPageFrag = new MyPageFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, homeFrag).commit();

        tabs.getTabAt(0).setIcon(R.drawable.home);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                setDefault();
                selected = null;
                if(position == 0){
                    selected = homeFrag;
                    tabs.getTabAt(0).setIcon(R.drawable.home);
                }else if (position == 1){
                    //selected = writingFrag;
                    //Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                   // startActivity(intent);
                }else if (position == 2){
                    selected = myPageFrag;
                    tabs.getTabAt(2).setIcon(R.drawable.mypage_1);
                }
                if(position == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                    startActivity(intent);
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if(tab.getPosition() == 1)
                {
                    Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                    startActivity(intent);
                }else{
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                }
            }
        });
    }

    public void setDefault(){
        tabs.getTabAt(0).setIcon(R.drawable.home_1);
        tabs.getTabAt(1).setIcon(R.drawable.write);
        tabs.getTabAt(2).setIcon(R.drawable.mypage);
    }

    public static int getUser() {
        return user;
    }

    public static void setUser(int user) {
        MainActivity.user = user;
    }
}