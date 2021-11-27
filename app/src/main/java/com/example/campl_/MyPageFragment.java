package com.example.campl_;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.lang.String.valueOf;


public class MyPageFragment extends Fragment {

    View view;
    CamplAPI camplAPI;
    Button signUpBtn;
    UserDTO user = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.add(this, "myPageFragment");
        camplAPI = MainActivity.camplAPI;

        if (MainActivity.getUser() != -1) {
            //일반 화면
            view = inflater.inflate(R.layout.fragment_my_page, container, false);
            setMyPageFragment();
        } else {
            view = inflater.inflate(R.layout.fragment_login, container, false);
            setLoginFragment();
        }
        return view;
    }


    void setMyPageFragment() {
        ImageView userImg = (ImageView) view.findViewById(R.id.userImg);
        Button myPlan = (Button) view.findViewById(R.id.myPage_plan);
        Button bookmark = (Button) view.findViewById(R.id.myPage_bookmark);
        Button logout = (Button) view.findViewById(R.id.logout);
        TextView tv = (TextView) view.findViewById(R.id.myPage_userName);

        getUserInfo(MainActivity.getUser());
        tv.setText(MainActivity.userObj.getNickName());
        Glide.with(getContext()).load("https://i.pinimg.com/474x/76/94/84/769484dafbe89bf2b8a22379658956c4.jpg").override(80, 80).circleCrop().into(userImg);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                camplAPI.postSignOut().enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            MainActivity.setUser(-1);
                            MainActivity.userObj = null;
                            refresh();
                        }
                        MainActivity.setUser(-1);
                        MainActivity.userObj = null;
                        refresh();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        });

        myPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //내 포스트 받아오기
                camplAPI.getMyPosts().enqueue(new Callback<List<PostDTO>>() {
                    @Override
                    public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                        ArrayList<PostDTO> searchPostsList = new ArrayList<>();
                        if (response.isSuccessful()) {
                            searchPostsList = (ArrayList<PostDTO>) response.body();
                        }
                        Log.e("myplan", String.valueOf(response.code()));

                        Intent intent = new Intent(getContext(), SearchResultActivity.class);
                        intent.putExtra("pageTitle", "내가 쓴 플랜");
                        intent.putExtra("posts", searchPostsList);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<PostDTO>> call, Throwable t) {

                    }
                });
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //내 북마크 글 받아오기

                camplAPI.getBookmarkList().enqueue(new Callback<List<PostDTO>>() {
                    @Override
                    public void onResponse(Call<List<PostDTO>> call, Response<List<PostDTO>> response) {
                        ArrayList<PostDTO> searchPosts = new ArrayList<>();
                        if (response.isSuccessful()) {
                            searchPosts = (ArrayList<PostDTO>) response.body();
                        }
                        Log.e("bookmark", String.valueOf(response.code()));

                        Intent intent = new Intent(getContext(), SearchResultActivity.class);
                        intent.putExtra("pageTitle", "내가 북마크한 플랜");
                        intent.putExtra("posts", searchPosts);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<List<PostDTO>> call, Throwable t) {

                    }
                });

            }
        });
    }

    void getUserInfo(int seq) {
       /* camplAPI.getUserInfo(seq).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if(response.isSuccessful()){
                    user = response.body();
                }
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {

            }
        });

        */
    }

    void setLoginFragment() {
        EditText input_id = (EditText) view.findViewById(R.id.id);
        EditText input_pw = (EditText) view.findViewById(R.id.pw);
        Button signIn = (Button) view.findViewById(R.id.signIn_login);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDTO user = new UserDTO(input_id.getText().toString(), input_pw.getText().toString());
                camplAPI.postSignIn(user).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("onResponse", "init");
                        if (response.code() == 200) {
                            MainActivity.setUser(1);
                            MainActivity.userObj = user;
                            refresh();
                        }
                        Log.e("login", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUpBtn = (Button) view.findViewById(R.id.signUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void refresh() {

        Fragment fr = getActivity().getSupportFragmentManager().findFragmentByTag("myPageFragment");
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ft.detach(this).commitNow();
            ft.attach(this).commitNow();
        } else {
            ft.detach(this).attach(this).commit();
        }
    }

}