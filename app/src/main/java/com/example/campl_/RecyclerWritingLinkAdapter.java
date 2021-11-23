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

    private HashMap<String, String> mData = new HashMap<>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerWritingLinkAdapter(HashMap<String, String> list) {
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
        final String item = mData.get(position);
        String[] arr = mData.keySet().toArray(new String[0]);

        holder.link.setText(mData.get(arr[position]));
        holder.name.setText(arr[position]);
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
        TextView name;
        TextView link;
        ImageButton delete;

        ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_name);
            link = itemView.findViewById(R.id.item_link);
            delete = (ImageButton) itemView.findViewById(R.id.deleteLink);
        }
    }

    public static <K, V> K getKey(Map<K, V> map, V value) {

        for (K key : map.keySet()) {
            if (value.equals(map.get(key))) {
                return key;
            }
        }
        return null;
    }
}
