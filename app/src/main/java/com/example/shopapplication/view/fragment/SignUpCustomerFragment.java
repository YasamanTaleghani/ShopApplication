package com.example.shopapplication.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.shopapplication.R;
import com.example.shopapplication.database.CustomerModel;
import com.example.shopapplication.database.ProductionModel;
import com.example.shopapplication.retrofit.NetworkParams;
import com.example.shopapplication.retrofit.customer.Billing;
import com.example.shopapplication.retrofit.customer.CustomerResponse;
import com.example.shopapplication.utilities.CustomerPreferences;
import com.example.shopapplication.view.activity.MainActivity;
import com.example.shopapplication.view.activity.SignUpCustomerActivity;
import com.example.shopapplication.viewmodel.ProductionViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.List;


public class SignUpCustomerFragment extends Fragment {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private ProductionViewModel mViewModel;

    private EditText mEditTextFirstName, mEditTextLastName, mEditTextMail, mEditTextAddress;
    private Button mButtonSubmit;
    private ImageButton mImageButtonMap;

    private FusedLocationProviderClient mClient;

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
        mClient = LocationServices.getFusedLocationProviderClient(getActivity());
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
        mEditTextAddress = view.findViewById(R.id.addredd_editText);
        mButtonSubmit = view.findViewById(R.id.submit);
        mImageButtonMap = view.findViewById(R.id.btn_map);
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
                        customer.getEmail(), getActivity());

                Toast.makeText(getActivity(), "کاربر جدید ثبت شد", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

        mImageButtonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean permission = setLocationRuntimePermission();
                if (permission) {
                    requestLocation();
                }
            }
        });
    }

    private void requestLocation() {
        if (ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setNumUpdates(1);
        locationRequest.setInterval(0);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationCallback locationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Location location = locationResult.getLocations().get(0);
            }
        };

        mClient.requestLocationUpdates
                (locationRequest,
                 locationCallback,
                 Looper.getMainLooper());
    }

    private boolean setLocationRuntimePermission(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission")
                        .setMessage("This app requires your location")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

}