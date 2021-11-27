package com.example.campl_;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerHotplaceMenuAdapter extends RecyclerView.Adapter<RecyclerHotplaceMenuAdapter.ViewHolder> {

    private ArrayList<String> mData = new ArrayList<>();
    private OnItemClickListener mListener = null;
    private int checked = 0;

    RecyclerHotplaceMenuAdapter(ArrayList<String> list) {
        mData = list;
    }

    @Override
    public RecyclerHotplaceMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_menu, parent, false);
        RecyclerHotplaceMenuAdapter.ViewHolder vh = new RecyclerHotplaceMenuAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(final RecyclerHotplaceMenuAdapter.ViewHolder holder, int position) {
        final String item = mData.get(position);

        String[] menuData = item.split("_");
        holder.menu.setText(menuData[0]);
        holder.price.setText(menuData[1]);
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
        TextView menu;
        TextView price;

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
            menu = itemView.findViewById(R.id.item_menu);
            price = itemView.findViewById(R.id.item_price);
        }
    }

}
