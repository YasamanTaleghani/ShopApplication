package com.example.shopapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapplication.R;
import com.example.shopapplication.retrofit.reviews.ReviewsResponse;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>{

    private Context mContext;
    private List<ReviewsResponse> mItems;

    public ReviewRecyclerViewAdapter(Context context, List<ReviewsResponse> items) {
        mContext = context;
        mItems = items;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).
                inflate(R.layout.reviews_recycler_view_row_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.mTextViewReviwer.setText(mItems.get(position).getReviewer());
        holder.mTextViewReview.setText(mItems.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewReviwer;
        private TextView mTextViewReview;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewReviwer = itemView.findViewById(R.id.review_text_reviewer);
            mTextViewReview = itemView.findViewById(R.id.review_text_review);
        }
    }
}
