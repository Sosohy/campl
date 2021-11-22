package com.example.campl_;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerDetailImageAdapter extends RecyclerView.Adapter<RecyclerDetailImageAdapter.ViewHolder> {

    private ArrayList<String> mData = new ArrayList<String>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerDetailImageAdapter(ArrayList<String> list) {
        mData = list;
    }

    @Override
    public RecyclerDetailImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_detail_image, parent, false);
        RecyclerDetailImageAdapter.ViewHolder vh = new RecyclerDetailImageAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerDetailImageAdapter.ViewHolder holder, int position) {

        final String item = mData.get(position);
        Glide.with(holder.itemView.getContext()).load(item).into(holder.img);
    }

    public String getImgUrl(int position) {
        return mData.get(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int pos, boolean checkBox);
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
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        String imgUrl = mData.get(pos);
                        //이미지 크게 띄우기?
                       // Intent intent = new Intent(view.getContext(), DetailActivity.class);
                       // intent.putExtra("img", imgUrl);
                       // view.getContext().startActivity(intent);
                    }
                }
            });

            img = itemView.findViewById(R.id.detailImage);
        }
    }
}
