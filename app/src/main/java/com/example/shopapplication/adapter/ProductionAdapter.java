package com.example.shopapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapplication.R;
import com.example.shopapplication.model.ProductionItem;

import java.util.ArrayList;
import java.util.List;

public class ProductionAdapter extends RecyclerView.Adapter<ProductionAdapter.ProductionHolder> {

    private Context mContext;
    private List<ProductionItem> mItems;

    public ProductionAdapter(Context context, List<ProductionItem> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public ProductionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(
                        R.layout.producton_item,
                        parent,
                        false);
        return new ProductionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductionHolder holder, int position) {
        holder.mTextView.setText(mItems.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ProductionHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;

        public ProductionHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.item_image);
            mTextView = itemView.findViewById(R.id.item_name);
        }
    }
}
