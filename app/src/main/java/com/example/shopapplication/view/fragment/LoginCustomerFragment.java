package com.example.shopapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopapplication.R;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.view.activity.SignUpCustomerActivity;
import com.example.shopapplication.viewmodel.ProductionViewModel;

import java.util.List;


public class LoginCustomerFragment extends Fragment {

    private ProductionViewModel mViewModel;

    private EditText mEditText;
    private Button mButton;

    public LoginCustomerFragment() {
        // Required empty public constructor
    }

    public static LoginCustomerFragment newInstance() {
        LoginCustomerFragment fragment = new LoginCustomerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel =  new ViewModelProvider(this).get(ProductionViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_customer, container, false);

        findViews(view);
        setListeners();

        return view;
    }

    private void setListeners() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isCustomerValid = false;
                List<CustomerModel> customers = mViewModel.returnAllCustomers();
                String phone = mEditText.toString();
                for (int i = 0; i < customers.size() ; i++) {
                    if (customers.get(i).phone.equals(phone)){
                        //TODO

                        isCustomerValid = true;
                    }
                }

                if (!isCustomerValid){
                    Intent intent = SignUpCustomerActivity.newIntent(getContext());
                    startActivity(intent);
                }
            }
        });
    }

    private void findViews(View view) {
        mEditText = view.findViewById(R.id.name_login_editText);
        mButton = view.findViewById(R.id.logon_submit);
    }
}