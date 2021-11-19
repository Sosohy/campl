package com.example.campl_;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;

public class RecyclerCardAdapter extends RecyclerView.Adapter<RecyclerCardAdapter.ViewHolder> {

    private ArrayList<PostDTO> mData = new ArrayList<PostDTO>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerCardAdapter(ArrayList<PostDTO> list) {
        mData = list;
    }

    @Override
    public RecyclerCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_card, parent, false);
        RecyclerCardAdapter.ViewHolder vh = new RecyclerCardAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerCardAdapter.ViewHolder holder, int position) {

        final PostDTO item = mData.get(position);

        holder.title.setText(item.getTitle());
        holder.timing.setText(item.getTimingType());
        holder.duration.setText(item.getDurationTimeType());
        holder.cost.setText(item.getCostType());

        String[] url = item.getPictureUrls();
        Glide.with(holder.itemView.getContext()).load(url[0]).into(holder.img);
    }
    
    public int getSeq(int position) {
        return mData.get(position).getSeq();
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
        TextView timing;
        TextView duration;
        TextView cost;
        ImageView img;

        ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        PostDTO cardData = mData.get(pos);
                        PostDTO p = new PostDTO();

                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("seq", cardData.getSeq());
                        view.getContext().startActivity(intent);
                    }
                }
            });

            title = itemView.findViewById(R.id.card_title);
            timing = itemView.findViewById(R.id.card_timing);
            duration = itemView.findViewById(R.id.card_duration);
            cost = itemView.findViewById(R.id.card_cost);
            img = itemView.findViewById(R.id.card_img);
        }
    }
}
