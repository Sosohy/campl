package com.example.campl_;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Multipart;


public class WritingActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    camplAPI camplAPI;
    View view;

    ArrayList<Integer> durationData = new ArrayList<>();
    ArrayList<Integer> timingData = new ArrayList<>();
    ArrayList<Integer> categoryData = new ArrayList<>();
    ArrayList<Integer> costData = new ArrayList<>();

    EditText title;
    EditText content;
    ImageButton back;
    ArrayList<MultipartBody.Part> files = new ArrayList<>();

    RecyclerView writing_recycler = null;
    ArrayList<String> imgArray = new ArrayList<>();
    RecyclerWritingImageAdapter ImageAdapter = new RecyclerWritingImageAdapter(imgArray);

    Button registerBtn;
    ImageButton uploadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_writing);
        setInitBtns();


        title = (EditText) findViewById(R.id.writing_plan);
        content = (EditText) findViewById(R.id.writing_content);

        camplAPI = MainActivity.camplAPI;
        registerBtn = (Button) findViewById(R.id.writing_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int seq = -1;
                String[] cData = new String[10];
                for(int i=0; i<categoryData.size(); i++)
                    cData[i] = camplAPI.categoryQuery.get(categoryData.get(i));
                PostDTO post = new PostDTO(title.getText().toString(), content.getText().toString(), camplAPI.costQuery.get(costData.get(0)), camplAPI.durationQuery.get(durationData.get(0)), camplAPI.timingQuery.get(timingData.get(0)), cData);
                camplAPI.writingPost(post).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody result = response.body();
                        if (response.isSuccessful()) {
                            //  seq = response.body();
                        }
                        Log.e("aa", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                    camplAPI.addImage(seq, files).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                            }
                            Toast.makeText(getApplicationContext(), "글이 등록되었습니다", Toast.LENGTH_SHORT);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    });
            }
        });


        back = (ImageButton) findViewById(R.id.writing_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uploadImg = (ImageButton) findViewById(R.id.writing_addImg);
        uploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imgArray.size() > 5)
                    Toast.makeText(getApplicationContext(), "사진을 5장 이상 추가할 수 없습니다.", Toast.LENGTH_SHORT).show();
                else{
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, GET_GALLERY_IMAGE);
                }
            }
        });

        writing_recycler = (RecyclerView) findViewById(R.id.writing_img_recycler);
        writing_recycler.setAdapter(ImageAdapter);
        ImageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            imgArray.add(selectedImageUri.toString());
            ImageAdapter.notifyDataSetChanged();

            File file = new File(selectedImageUri.getPath());
            RequestBody rb = RequestBody.create(MediaType.parse("image/jpeg"), selectedImageUri.getPath());
            files.add(MultipartBody.Part.createFormData("file", "file" + files.size(), rb));
        }
    }

    void setInitBtns() {

        LinearLayout whenLayout = (LinearLayout) findViewById(R.id.writing_when);
        LinearLayout durationLayout = (LinearLayout) findViewById(R.id.writing_duration);
        LinearLayout categoryLayout = (LinearLayout) findViewById(R.id.writing_category);
        LinearLayout costLayout = (LinearLayout) findViewById(R.id.writing_cost);

        for (int i = 0; i < camplAPI.timingD.length; i++) {
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv.isSelected()) {
                        tv.setSelected(false);
                        timingData.remove(Integer.valueOf(idx));
                    } else {
                        tv.setSelected(true);
                        timingData.add(idx);
                    }
                }
            });
            ;
            tv.setBackgroundResource(camplAPI.xmlData[i]);
            tv.setText(camplAPI.timingD[i]);
            tv.setPadding(10, 0, 10, 0);
            whenLayout.addView(tv);
        }

        for (int i = 0; i < camplAPI.durationD.length; i++) {
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv.isSelected()) {
                        tv.setSelected(false);
                        durationData.remove(Integer.valueOf(idx));
                    } else {
                        tv.setSelected(true);
                        durationData.add(idx);
                    }
                }
            });
            ;
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(camplAPI.durationD[i]);
            tv.setPadding(10, 0, 10, 0);
            durationLayout.addView(tv);
        }

        GridLayout gl = new GridLayout(getApplicationContext());
        gl.setColumnCount(4);
        for (int i = 0; i < camplAPI.categoryD.length; i++) {
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv.isSelected()) {
                        tv.setSelected(false);
                        categoryData.remove(Integer.valueOf(idx));
                    } else {
                        tv.setSelected(true);
                        categoryData.add(idx);
                    }
                }
            });
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(camplAPI.categoryD[i]);
            tv.setPadding(10, 0, 10, 0);
            gl.addView(tv);
        }
        categoryLayout.addView(gl);

        for (int i = 0; i < camplAPI.costD.length; i++) {
            Button tv = new Button(getApplicationContext());
            int idx = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tv.isSelected()) {
                        tv.setSelected(false);
                        costData.remove(Integer.valueOf(idx));
                    } else {
                        tv.setSelected(true);
                        costData.add(idx);
                    }
                }
            });
            tv.setBackgroundResource(R.drawable.btn_default);
            tv.setText(camplAPI.costD[i]);
            tv.setPadding(10, 0, 10, 0);
            costLayout.addView(tv);
        }
    }

}