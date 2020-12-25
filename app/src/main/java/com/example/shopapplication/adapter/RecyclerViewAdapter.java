package com.example.shopapplication.adapter;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopapplication.R;
import com.example.shopapplication.model.ProductionItem;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder>{

    private Context mContext;
    private ArrayList<ProductionItem> mItems;

    public RecyclerViewAdapter(Context context, ArrayList<ProductionItem> items) {
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
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.viewHolder holder, int position) {
        holder.mTextView.setText(mItems.get(position).getTitle());

        //TODO
        /*byte[] image = getAlbumArt(mItems.get(position).get);
        if (image != null){
            Glide.with(mContext)
                    .asBitmap()
                    .load(image)
                    .into(holder.mImageView);
        }*/
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mTextView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.recyclerView_row_item_imageView);
            mTextView = itemView.findViewById(R.id.recyclerView_row_item_textView);
        }
    }

    private byte[] getAlbumArt(String uri){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(uri);
        byte[] art = retriever.getEmbeddedPicture();
        retriever.release();
        return art;
    }
}