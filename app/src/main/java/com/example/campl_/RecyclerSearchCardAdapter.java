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

public class RecyclerSearchCardAdapter extends RecyclerView.Adapter<RecyclerSearchCardAdapter.ViewHolder> {

    private ArrayList<PostDTO> mData = new ArrayList<PostDTO>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerSearchCardAdapter(ArrayList<PostDTO> list) {
        mData = list;
    }

    @Override
    public RecyclerSearchCardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_search_card, parent, false);
        RecyclerSearchCardAdapter.ViewHolder vh = new RecyclerSearchCardAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerSearchCardAdapter.ViewHolder holder, int position) {

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

                        Intent intent = new Intent(view.getContext(), DetailActivity.class);
                        intent.putExtra("seq", cardData.getSeq());
                        view.getContext().startActivity(intent);
                    }
                }
            });

            title = itemView.findViewById(R.id.searchCard_title);
            timing = itemView.findViewById(R.id.searchCard_timing);
            duration = itemView.findViewById(R.id.searchCard_duration);
            cost = itemView.findViewById(R.id.searchCard_cost);
            img = itemView.findViewById(R.id.searchCard_img);
        }
    }
}