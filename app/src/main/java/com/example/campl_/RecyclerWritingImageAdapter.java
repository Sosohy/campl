package com.example.campl_;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;

public class RecyclerWritingImageAdapter extends RecyclerView.Adapter<RecyclerWritingImageAdapter.ViewHolder> {

    private ArrayList<String> mData = new ArrayList<String>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerWritingImageAdapter(ArrayList<String> list) {
        mData = list;
    }

    @Override
    public RecyclerWritingImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_detail_image, parent, false);
        RecyclerWritingImageAdapter.ViewHolder vh = new RecyclerWritingImageAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerWritingImageAdapter.ViewHolder holder, int position) {
        final String item = mData.get(position);

        if(item != null){
            if(item.contains("http"))
                Glide.with(holder.itemView.getContext()).load(item).override(160, 160).centerCrop().into(holder.img);
            else
                holder.img.setImageURI(Uri.parse(item));
        }


    }

    public String getImgUrl(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        if(mListener != null)
                            mListener.onItemClick(view, pos);
                    }
                }
            });
            img = itemView.findViewById(R.id.detailImage);
        }
    }
}
