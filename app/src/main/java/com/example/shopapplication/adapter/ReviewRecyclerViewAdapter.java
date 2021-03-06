package com.example.shopapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapplication.R;
import com.example.shopapplication.retrofit.reviews.ReviewsResponse;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.view.activity.LoginCustomerActivity;
import com.example.shopapplication.view.fragment.ReviewDialogFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>{

    private Context mContext;
    private List<ReviewsResponse> mItems;
    private Activity mActivity;

    public ReviewRecyclerViewAdapter(Context context, List<ReviewsResponse> items, Activity activity) {
        mContext = context;
        mItems = items;
        mActivity = activity;
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
        Document document = Jsoup.parse(mItems.get(position).getReview());
        Elements paragraphs = document.getElementsByTag("p");
        holder.mTextViewReview.setText(paragraphs.text());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int reviewId = mItems.get(position).getId();
                ReviewDialogFragment reviewDialogFragment = ReviewDialogFragment.newInstance(reviewId);
                FragmentActivity fr = (FragmentActivity) mContext;
                FragmentManager fm = fr.getSupportFragmentManager();
                reviewDialogFragment.show(fm , "reviewTag");
            }
        });
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
