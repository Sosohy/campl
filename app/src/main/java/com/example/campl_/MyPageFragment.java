package com.example.campl_;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MyPageFragment extends Fragment {

    View view;
    camplAPI camplAPI;
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

        if(MainActivity.getUser() != -1){
            //일반 화면

            view = inflater.inflate(R.layout.fragment_my_page, container, false);

            ImageView userImg = (ImageView)view.findViewById(R.id.userImg);
            Button myPlan = (Button)view.findViewById(R.id.myPage_plan);
            Button bookmark = (Button)view.findViewById(R.id.myPage_bookmark);
            Button logout = (Button)view.findViewById(R.id.logout);

            getUserInfo(MainActivity.getUser());

            user = new UserDTO();
            user.setUserImage("https://www.culture.go.kr/upload/editor_upload/2020/01/2001060314.jpg");
            Glide.with(getContext()).load(user.getUserImage()).override(80, 80).circleCrop().into(userImg);

            myPlan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //내 포스트 받아오기
                    ArrayList<PostDTO> searchPosts = new ArrayList<>();

                    Intent intent = new Intent(getContext(), SearchResultActivity.class);
                    intent.putExtra("pageTitle", "내가 쓴 플랜");
                    intent.putExtra("posts", searchPosts);
                    startActivity(intent);
                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //내 북마크 글 받아오기
                    ArrayList<PostDTO> searchPosts = new ArrayList<>();

                    Intent intent = new Intent(getContext(), SearchResultActivity.class);
                    intent.putExtra("pageTitle", "내가 북마크한 플랜");
                    intent.putExtra("posts", searchPosts);
                    startActivity(intent);
                }
            });

            //setMyPageFragment();
        }
        else{
            view = inflater.inflate(R.layout.fragment_login, container, false);
            setLoginFragment();
        }

        return view;
    }


    void getUserInfo(int seq){
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

    void setLoginFragment(){
        EditText input_id = (EditText)view.findViewById(R.id.id);
        EditText input_pw = (EditText)view.findViewById(R.id.pw);
        Button signIn = (Button)view.findViewById(R.id.signIn_login);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDTO user = new UserDTO(input_id.toString(), input_pw.toString());
                camplAPI.postSignIn(user).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("onResponse", "init");
                        try {
                            if(response.isSuccessful()){
                                Toast.makeText(getContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                            }
                            MainActivity.setUser(1);
                            MainActivity.userDTO = user;
                            refresh();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signUpBtn = (Button)view.findViewById(R.id.signUp);
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void refresh(){

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