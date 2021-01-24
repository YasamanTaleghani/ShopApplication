package com.example.shopapplication.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        mViewModel = new ViewModelProvider(this).get(ProductionViewModel.class);
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
                boolean isLogin = false;


                String mail = mEditText.getText().toString();
                String sharedPref = CustomerPreferences.getCustomerIfPreferences
                        (getActivity(), CustomerPreferences.PREF_SHOP_LIST);
                Log.d("test", mail);
                Log.d("test", sharedPref + "");

                if (mail.equals(CustomerPreferences.getCustomerIfPreferences
                        (getActivity(), CustomerPreferences.PREF_SHOP_LIST))) {
                    Toast.makeText(getActivity(), "شما وارد شدید", Toast.LENGTH_SHORT).show();
                    isLogin = true;
                    getActivity().finish();
                }

                /*mViewModel.returnAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<CustomerModel>>() {
                    @Override
                    public void onChanged(List<CustomerModel> customers) {
                        if (customers!=null){
                            for (int i = 0; i < customers.size() ; i++) {
                                if (customers.get(i).mail.equals(mail)){
                                    CustomerPreferences.putCustomerInPreferences
                                            (getActivity(), customers.get(i).mail);

                                    Toast.makeText(getActivity(), "شما وارد شدید", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            }
                        }
                    }
                });*/

                mViewModel.fetchCustomers();
                boolean finalIsLogin = isLogin;
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
        String mail = mEditText.getText().toString();
        Log.d("customer", "inserted phone is: " + mail);
        CustomerResponse customer = new CustomerResponse();

        for (int i = 0; i < customerResponses.size(); i++) {
            Log.d("customer", customerResponses.get(i).getEmail());
            if (customerResponses.get(i).getEmail().equals(mail)) {
                customer = customerResponses.get(i);
                isCustomerValid = true;
                break;
            }
        }

        if (isCustomerValid) {
            CustomerModel newCustomer = new CustomerModel(
                    customer.getId(),
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail());

            mViewModel.insertCustomer(newCustomer);
            CustomerPreferences.putCustomerInPreferences(getActivity(), newCustomer.mail);
            CustomerPreferences.putCustomerId(getActivity(), customer.getId());
            Toast.makeText(getActivity(), "شما وارد شدید", Toast.LENGTH_SHORT).show();
            getActivity().finish();
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