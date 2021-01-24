package com.example.shopapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopapplication.R;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.model.ProductsItem;

import java.util.List;

public class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopListViewHolder>{

    public static final String EXTRA_PRODUCT_ID = "com.example.shopapplication.adapter.product_id";
    private Context mContext;
    private List<ProductionModel> mItems;

    public ShopListAdapter(Context context, List<ProductionModel> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public ShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(
                R.layout.shop_list_row_item, parent, false);
        return new ShopListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListViewHolder holder, int position) {

        Glide
                .with(mContext)
                .load(mItems.get(position).productionSrc)
                .placeholder(R.drawable.ic_loading)
                .into(holder.mImageView);

        holder.mTextView.setText(mItems.get(position).productionName);
        holder.mTextViewPrice.setText(mItems.get(position).ProductionPrice + "  هزار تومان");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ShopListViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;
        private TextView mTextViewPrice;

        public ShopListViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.row_item_image);
            mTextView = itemView.findViewById(R.id.row_item_text1);
            mTextViewPrice = itemView.findViewById(R.id.row_item_text2);
        }
    }
}
