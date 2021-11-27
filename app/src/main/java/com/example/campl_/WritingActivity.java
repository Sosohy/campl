package com.example.campl_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WritingActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    CamplAPI camplAPI;
    View view;

    ArrayList<Integer> durationData = new ArrayList<>();
    ArrayList<Integer> timingData = new ArrayList<>();
    ArrayList<Integer> categoryData = new ArrayList<>();
    ArrayList<Integer> costData = new ArrayList<>();

    EditText title;
    EditText content;
    ImageButton back;
    ArrayList<MultipartBody.Part> files = new ArrayList<>();

    RecyclerView writingImg_recycler = null;
    ArrayList<String> imgArray = new ArrayList<>();
    RecyclerWritingImageAdapter ImageAdapter = new RecyclerWritingImageAdapter(imgArray);

    RecyclerView link_recycler = null;
    ArrayList<UrlDTO> urls = new ArrayList<>();
    RecyclerWritingLinkAdapter linkAdapter = new RecyclerWritingLinkAdapter(urls);

    Button registerBtn;
    ImageButton uploadImg;
    ImageButton uploadUrl;
    Dialog urlDialog;
    Dialog configDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_writing);

        camplAPI = MainActivity.camplAPI;
        setInitBtns();

        title = (EditText) findViewById(R.id.writing_plan);
        content = (EditText) findViewById(R.id.writing_content);
        uploadImg = (ImageButton) findViewById(R.id.writing_addImg);
        uploadUrl = (ImageButton)findViewById(R.id.writing_url);

        configDialog = new Dialog(WritingActivity.this);
        configDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        configDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        configDialog.setContentView(R.layout.custom_dialog);

        urlDialog = new Dialog(WritingActivity.this);
        urlDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        urlDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        urlDialog.setContentView(R.layout.custom_dialog_url);

        registerBtn = (Button) findViewById(R.id.writing_register);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSave();
            }
        });

        back = (ImageButton) findViewById(R.id.writing_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        uploadUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUrl();
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

        writingImg_recycler = (RecyclerView) findViewById(R.id.writing_img_recycler);
        writingImg_recycler.setAdapter(ImageAdapter);
        ImageAdapter.notifyDataSetChanged();

        link_recycler = (RecyclerView) findViewById(R.id.link_recycler);
        linkAdapter.setOnItemClickListener(new RecyclerWritingLinkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                urls.remove(pos);
                linkAdapter.notifyDataSetChanged();
            }
        });
        link_recycler.setAdapter(linkAdapter);
        linkAdapter.notifyDataSetChanged();
    }

    String getUrlString(ArrayList<UrlDTO> urlDTOS){
        if(urlDTOS.size() ==0) return "";

        String s = "";
        for(int i=0; i<urlDTOS.size(); i++)
            s += (urlDTOS.get(i).toString() + ",");

        return s;
    }


    public void showDialogUrl(){
        urlDialog.show();

        EditText name = (EditText)urlDialog.findViewById(R.id.url_name);
        EditText link = (EditText)urlDialog.findViewById(R.id.url_link);

        Button noBtn = urlDialog.findViewById(R.id.url_no);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setText("");
                link.setText("");
                urlDialog.dismiss();
            }
        });

        urlDialog.findViewById(R.id.url_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                urls.add(new UrlDTO(name.getText().toString(), link.getText().toString()));
                name.setText("");
                link.setText("");
                urlDialog.dismiss();
            }
        });
    }

    public void showDialogSave(){
        configDialog.show();

        configDialog.findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timingData.size() == 0 || categoryData.size() == 0 || costData.size() == 0 || timingData.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), "각 조건을 하나 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int seq = 0;
                ArrayList<String> cData = new ArrayList<>();
                if(categoryData.size() != 0){
                    for(int i=0; i<categoryData.size(); i++)
                        cData.add(camplAPI.categoryQuery.get(categoryData.get(i)));
                }else
                    cData.add("");

                PostDTO post = new PostDTO(title.getText().toString(), content.getText().toString(), getUrlString(urls), camplAPI.costQuery.get(costData.get(0)), camplAPI.durationQuery.get(durationData.get(0)), camplAPI.timingQuery.get(timingData.get(0)), cData.toArray(new String[cData.size()]));
                camplAPI.writingPost(post).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody result = response.body();
                        if (response.isSuccessful()) {
                            //  seq = response.body();
                            Toast.makeText(getApplicationContext(), "글이 저장되었습니다", Toast.LENGTH_SHORT);

                            Intent intent = new Intent(view.getContext(), DetailActivity.class);
                            intent.putExtra("post", post);
                            startActivity(intent);
                        }
                        Log.e("writing", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                if(files.size() != 0){
                    camplAPI.addImage(seq, files).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                }
                finish();
            }
        });

        Button noBtn = configDialog.findViewById(R.id.no);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configDialog.dismiss();
            }
        });
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