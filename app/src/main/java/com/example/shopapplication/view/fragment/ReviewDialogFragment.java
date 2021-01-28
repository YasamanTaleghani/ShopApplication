package com.example.shopapplication.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopapplication.R;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.view.activity.LoginCustomerActivity;
import com.example.shopapplication.viewmodel.ProductionViewModel;


public class ReviewDialogFragment extends DialogFragment {

    public static final String ARG_REVIEW_ID = "arg_reviewId";

    private ProductionViewModel mViewModel;
    private EditText mEditTextReviewEdit, mEditTextRatingEdit;
    private Button mButtonSave, mButtonDelete, mButtonCancel;
    private int reviewId;

    public ReviewDialogFragment() {
        // Required empty public constructor
    }

    public static ReviewDialogFragment newInstance(int reviewId) {
        ReviewDialogFragment fragment = new ReviewDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_REVIEW_ID, reviewId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            reviewId = getArguments().getInt(ARG_REVIEW_ID);
        }
        mViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_review_dialog, null);

        @SuppressLint("ResourceType")
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);

        AlertDialog dialog = builder.create();

        findViews(view);
        setOnListeners();

        return dialog;
    }

    private void findViews(View view) {
        mEditTextReviewEdit = view.findViewById(R.id.review_editText);
        mEditTextRatingEdit = view.findViewById(R.id.rating_editText);
        mButtonCancel = view.findViewById(R.id.btn_cancel_reiew_update);
        mButtonDelete = view.findViewById(R.id.btn_delete_review);
        mButtonSave = view.findViewById(R.id.btn_save_review_update);
    }

    private void setOnListeners() {
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String review = mEditTextReviewEdit.getText().toString();
                int rating = Integer.parseInt(mEditTextRatingEdit.getText().toString());
                String reviewer = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(), CustomerPreferences.CUSTOMER_NAME);
                String reviewerMail = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(), CustomerPreferences.CUSTOMER_MAIL);

                if (reviewer==null || reviewerMail==null){
                    Intent intent = LoginCustomerActivity.newIntent(getContext());
                    startActivity(intent);
                }

                mViewModel.updateReview
                        (reviewId, review, reviewer, reviewerMail, rating, getActivity());

                dismiss();
            }
        });

        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewer = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(), CustomerPreferences.CUSTOMER_NAME);
                String reviewerMail = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(), CustomerPreferences.CUSTOMER_MAIL);

                if (reviewer==null || reviewerMail==null){
                    Intent intent = LoginCustomerActivity.newIntent(getContext());
                    startActivity(intent);
                }

                mViewModel.deleteReview(reviewId, getActivity());

                dismiss();
            }
        });
    }
}