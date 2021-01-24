package com.example.shopapplication.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.shopapplication.R;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.utilities.CustomerPreferences;
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

                mViewModel.fetchCustomers();
                mViewModel.getCustomersLiveData().observe(getViewLifecycleOwner(), new Observer<List<CustomerResponse>>() {
                    @Override
                    public void onChanged(List<CustomerResponse> customerResponses) {
                        checkCustomer(customerResponses);
                    }
                });

            }
        });
    }

    private void checkCustomer(List<CustomerResponse> customerResponses) {
        boolean isCustomerValid = false;
        String phone = mEditText.toString();
        CustomerResponse customer = new CustomerResponse();

        for (int i = 0; i < customerResponses.size(); i++) {
            if (customerResponses.get(i).getBilling().getPhone().equals(phone)){
                customer = customerResponses.get(i);
                isCustomerValid = true;
                break;
            }
        }

        if (isCustomerValid){
            CustomerModel customerModel = mViewModel.getCustomer(customer.getId());
            if (customerModel==null){
                CustomerModel newCustomer = new CustomerModel(
                                customer.getId(),
                                customer.getFirstName(),
                                customer.getLastName(),
                                customer.getBilling().getPhone(),
                                customer.getEmail(),
                                customer.getBilling().getCity(),
                                customer.getBilling().getState(),
                                customer.getBilling().getCountry(),
                                customer.getBilling().getAddress1(),
                                customer.getBilling().getAddress2());

                mViewModel.insertCustomer(newCustomer);
                CustomerPreferences.putCustomerInPreferences(getActivity(), newCustomer.customerId);
            }
        } else {
            Intent intent = SignUpCustomerActivity.newIntent(getContext());
            startActivity(intent);
        }
    }

    private void findViews(View view) {
        mEditText = view.findViewById(R.id.name_login_editText);
        mButton = view.findViewById(R.id.logon_submit);
    }
}