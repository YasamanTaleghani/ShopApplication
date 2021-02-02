package com.example.shopapplication.view.activity;

import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.example.shopapplication.R;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PointOfInterest;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public static final String EXTRA_CURRENT_LAT = "extra_current_lat";
    public static final String EXTRA_CURRENT_LNG = "extra_current_lng";
    private GoogleMap mMap;
    private LatLng mLatLngCurrent;
    private LatLng mLatLngNew;

    public static Intent newIntent(Context context, double currentLat, double currentLng) {
        Intent intent =  new Intent(context, MapsActivity.class);
        intent.putExtra(EXTRA_CURRENT_LAT, currentLat);
        intent.putExtra(EXTRA_CURRENT_LNG, currentLng);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        double currentLat = intent.getDoubleExtra(EXTRA_CURRENT_LAT, 0);
        double currentLng = intent.getDoubleExtra(EXTRA_CURRENT_LNG, 0);
        mLatLngCurrent = new LatLng(currentLat, currentLng);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(mLatLngCurrent).title("موقعیت فعلی"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mLatLngCurrent));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setOnPoiClickListener(new GoogleMap.OnPoiClickListener() {
            @Override
            public void onPoiClick(PointOfInterest pointOfInterest) {

                Log.d("place",  "lat: " + pointOfInterest.latLng.latitude +
                        "log: " + pointOfInterest.latLng.longitude);
            }
        });
    }


}