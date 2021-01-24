package com.example.shopapplication.view.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shopapplication.R;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.customer.Billing;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.viewmodel.ProductionViewModel;


public class SignUpCustomerFragment extends Fragment {

    private ProductionViewModel mViewModel;

    private EditText mEditTextFirstName, mEditTextLastName, mEditTextMail;
    private Button mButtonSubmit;

    public SignUpCustomerFragment() {
        // Required empty public constructor
    }

    public static SignUpCustomerFragment newInstance() {
        SignUpCustomerFragment fragment = new SignUpCustomerFragment();
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
        View view =
                inflater.inflate(R.layout.fragment_signup_customer, container, false);

        findViews(view);
        setListeners();

        return view;
    }

    private void findViews(View view) {
        mEditTextFirstName = view.findViewById(R.id.name_editText);
        mEditTextLastName = view.findViewById(R.id.lastName_editText);
        mEditTextMail = view.findViewById(R.id.mail_editText);

        mButtonSubmit = view.findViewById(R.id.submit);
    }

    private void setListeners() {
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CustomerResponse customer =
                        new CustomerResponse(
                                mEditTextFirstName.getText().toString(),
                                mEditTextLastName.getText().toString(),
                                new Billing(),
                                mEditTextMail.getText().toString());

                mViewModel.postCustomer(customer.getFirstName(), customer.getLastName(),
                        customer.getEmail());

                CustomerModel customerModel = new CustomerModel(
                        customer.getId(),
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail());

                mViewModel.insertCustomer(customerModel);
                CustomerPreferences.putCustomerInPreferences(getActivity(), customer.getEmail());
                Toast.makeText(getActivity(), "کاربر جدید ثبت شد", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
    }
}