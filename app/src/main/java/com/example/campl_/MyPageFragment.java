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
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MyPageFragment extends Fragment {

    View view;
    camplAPI camplAPI;
    Button signUpBtn;

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
        }
        else{
            view = inflater.inflate(R.layout.fragment_login, container, false);
            setLoginFragment();
        }

        return view;
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

                                MainActivity.setUser(1);
                                refresh();
                            }

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