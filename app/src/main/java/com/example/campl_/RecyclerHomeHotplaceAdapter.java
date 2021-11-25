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

public class RecyclerHomeHotplaceAdapter extends RecyclerView.Adapter<RecyclerHomeHotplaceAdapter.ViewHolder> {

    private ArrayList<PlaceDTO> mData = new ArrayList<PlaceDTO>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerHomeHotplaceAdapter(ArrayList<PlaceDTO> list) {
        mData = list;
    }

    @Override
    public RecyclerHomeHotplaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_hotplace, parent, false);
        RecyclerHomeHotplaceAdapter.ViewHolder vh = new RecyclerHomeHotplaceAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerHomeHotplaceAdapter.ViewHolder holder, int position) {

        final PlaceDTO item = mData.get(position);

        holder.title.setText(item.getName());
        String url = item.getImgUrl();

        if(url != null)
        Glide.with(holder.itemView.getContext()).load(url).override(200, 130).centerCrop().into(holder.img);
    }
    
    public String getSeq(int position) {
        return mData.get(position).getName();
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
        TextView title;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        PlaceDTO cardData = mData.get(pos);

                        Intent intent = new Intent(view.getContext(), DetailOfPlaceActivity.class);
                        intent.putExtra("place", cardData);
                        intent.putExtra("name", cardData.getName());
                        view.getContext().startActivity(intent);
                    }
                }
            });

            title = itemView.findViewById(R.id.cardPlace_title);
            img = itemView.findViewById(R.id.cardPlace_img);
        }
    }
}
