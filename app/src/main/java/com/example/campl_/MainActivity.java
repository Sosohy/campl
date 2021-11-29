package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<PostDTO> postExample = new ArrayList<>();
    public static ArrayList<PlaceDTO> placeExample = new ArrayList<>();
    public static ArrayList<PostDTO> myPostExample = new ArrayList<>();
    public static ArrayList<PostDTO> bookmarkExample = new ArrayList<>();

    public static ArrayList<PostDTO> popularPostsM = new ArrayList<PostDTO>();
    public static ArrayList<PostDTO> recommandPostsM = new ArrayList<PostDTO>();
    public static ArrayList<PlaceDTO> hotplacePostsM = new ArrayList<>();

    private static int user = -1;
    public static UserDTO userObj = null;
    public static Retrofit retrofit;
    public static CamplAPI camplAPI;
    public static boolean load = false;

    TabLayout tabs;
    Fragment selected;

    Fragment homeFrag, writingFrag, myPageFrag;

    public static final int[] tabIcon = {R.drawable.home_1, R.drawable.write, R.drawable.mypage};
    public static final int[] tabClickIcon = {R.drawable.home, R.drawable.photo, R.drawable.mypage_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder().setLenient().create();

        final CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient.Builder()
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();

        retrofit = new Retrofit.Builder().client(client).baseUrl(CamplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        camplAPI = retrofit.create(CamplAPI.class);

        Intent intent = getIntent();

        tabs = (TabLayout) findViewById(R.id.tabLayout);

        homeFrag = new HomeFragment();
        writingFrag = new WritingFragment();
        myPageFrag = new MyPageFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable("popular", (ArrayList<PostDTO>)intent.getSerializableExtra("popular") );
        bundle.putSerializable("recommand", (ArrayList<PostDTO>)intent.getSerializableExtra("recommand") );
        bundle.putSerializable("hotplace", (ArrayList<PlaceDTO>)intent.getSerializableExtra("popular") );
        homeFrag.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().add(R.id.frame, myPageFrag).commit();

        setCustomTabs();
        setClickIcon(2);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();

                if(position != 1)
                    setCustomTabs();
                selected = null;
                if (position == 0) {
                    if (MainActivity.getUser() == -1){
                        Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                        selected = myPageFrag;
                        setClickIcon(2);
                    }
                    else{
                        selected = homeFrag;
                        setClickIcon(position);
                    }
                } else if (position == 2) {
                    selected = myPageFrag;
                    setClickIcon(position);
                }

                if (position == 1) {
                    if (MainActivity.getUser() == -1) {
                        Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                        startActivity(intent);
                    }
                } else {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1) {
                    Intent intent = new Intent(getApplicationContext(), WritingActivity.class);
                    startActivity(intent);
                } else {
                    setCustomTabs();
                    int position = tab.getPosition();

                    if (position == 0) {
                        if (MainActivity.getUser() == -1){
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 서비스입니다.", Toast.LENGTH_SHORT).show();
                            selected = myPageFrag;
                            setClickIcon(2);
                        }
                        else{
                            selected = homeFrag;
                            setClickIcon(position);
                        }
                    } else if (position == 2) {
                        selected = myPageFrag;
                        setClickIcon(position);
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, selected).commit();
                }
            }
        });
    }

    private void setCustomTabs() {
        for (int i = 0; i < tabIcon.length; i++) {
            View view = getLayoutInflater().inflate(R.layout.customtab,null);
            TabLayout.Tab tab = tabs.getTabAt(i);
            view.findViewById(R.id.tab_icon).setBackgroundResource(tabIcon[i]);
            if(tab!=null){
                tab.setCustomView(null);
                tab.setCustomView(view);
            }
        }
    }

    void setClickIcon(int clickIcon){
        View view = getLayoutInflater().inflate(R.layout.customtab,null);
        TabLayout.Tab t = tabs.getTabAt(clickIcon);
        view.findViewById(R.id.tab_icon).setBackgroundResource(tabClickIcon[clickIcon]);
        tabs.getTabAt(clickIcon).setCustomView(null);
        tabs.getTabAt(clickIcon).setCustomView(view);
    }


    public static int getUser() {
        return user;
    }

    public static void setUser(int user) {
        MainActivity.user = user;
    }




}