package com.example.shopapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopapplication.R;
import com.example.shopapplication.retrofit.model.ImagesItem;
import com.example.shopapplication.view.activity.DetailActivity;
import com.example.shopapplication.retrofit.model.ProductsItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>{

    public static final String EXTRA_PRODUCTION_ID = "com.example.shopapplication.adapter.production_id";
    private Context mContext;
    private List<ProductsItem> mItems;

    public RecyclerViewAdapter(Context context, List<ProductsItem> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(mContext).inflate(
                        R.layout.recyclerview_row_item,
                        parent,
                        false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.mTextView.setText(mItems.get(position).getName());
        List<ImagesItem> images =(mItems.get(position).getImages());
        if (images.size()>0){
            Glide
                    .with(mContext)
                    .load(images.get(0).getSrc())
                    .placeholder(R.drawable.ic_loading)
                    .into(holder.mImageView);
        }
        holder.mTextViewPrice.setText(mItems.get(position).getPrice() + " تومان ");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = DetailActivity.newIntent(view.getContext());
                intent.putExtra(EXTRA_PRODUCTION_ID, mItems.get(position).getId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;
        private TextView mTextViewPrice;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.recyclerView_row_item_imageView);
            mTextView = itemView.findViewById(R.id.recyclerView_row_item_textView);
            mTextViewPrice = itemView.findViewById(R.id.recyclerView_row_item_textView_price);
        }
    }

}