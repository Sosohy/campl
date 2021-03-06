package com.example.campl_;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {

    public static Retrofit retrofit;
    public static CamplAPI camplAPI;

    EditText input_id;
    EditText input_pw;
    EditText input_name;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        input_id = (EditText)findViewById(R.id.input_id);
        input_pw = (EditText)findViewById(R.id.input_pw);
        input_name = (EditText)findViewById(R.id.input_name);
        signUp = (Button)findViewById(R.id.btn_submit);

        retrofit = new Retrofit.Builder().baseUrl(camplAPI.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        camplAPI = retrofit.create(CamplAPI.class);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                UserDTO user = new UserDTO(input_id.getText().toString(), input_pw.getText().toString(), input_name.getText().toString());
                camplAPI.postSignUp(user).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.e("onResponse", "init");
                        if(response.isSuccessful())
                            Toast.makeText(getApplicationContext(), "??????????????? ?????????????????????", Toast.LENGTH_SHORT).show();
                        else{
                            Log.e("er", String.valueOf(response.code() + " / " + response.errorBody()));
                        }
                        finish();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}