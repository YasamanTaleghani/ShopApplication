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
import com.example.shopapplication.retrofit.Products.LoadingProduct;
import com.example.shopapplication.retrofit.Products.ProductsItem;

import java.util.List;

public class InitialLoadingAdapter extends RecyclerView.Adapter<InitialLoadingAdapter.initialViewHolder>{

    public static final String EXTRA_PRODUCTION_ID = "com.example.shopapplication.adapter.production_id";
    private Context mContext;
    private List<LoadingProduct> mItems;

    public InitialLoadingAdapter(Context context, List<LoadingProduct> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public initialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(
                        R.layout.recyclerview_row_item,
                        parent,
                        false);
        return new initialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull initialViewHolder holder, int position) {
        holder.mTextView.setText("در حال بارگذاری");
        holder.mTextViewPrice.setText("در حال بارگذاری");
        holder.mImageView.setImageResource(R.drawable.ic_loading);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class initialViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;
        private TextView mTextViewPrice;

        public initialViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.recyclerView_row_item_imageView);
            mTextView = itemView.findViewById(R.id.recyclerView_row_item_textView);
            mTextViewPrice = itemView.findViewById(R.id.recyclerView_row_item_textView_price);
        }
    }
}
