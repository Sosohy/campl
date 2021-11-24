package com.example.campl_;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RecyclerWritingLinkAdapter extends RecyclerView.Adapter<RecyclerWritingLinkAdapter.ViewHolder> {

    private ArrayList<UrlDTO> mData = new ArrayList<>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerWritingLinkAdapter(ArrayList<UrlDTO> list) {
        mData = list;
    }

    @Override
    public RecyclerWritingLinkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_link, parent, false);
        RecyclerWritingLinkAdapter.ViewHolder vh = new RecyclerWritingLinkAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerWritingLinkAdapter.ViewHolder holder, int position) {
        final UrlDTO item = mData.get(position);

        holder.link.setText(item.getLink());
        holder.name.setText(item.getName());
    }

    public UrlDTO getImgUrl(int position) {
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
        TextView name;
        TextView link;

        ViewHolder(View itemView) {
            super(itemView);

            // 아이템 클릭 이벤트 처리.
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
            name = itemView.findViewById(R.id.item_name);
            link = itemView.findViewById(R.id.item_link);
        }
    }

}
