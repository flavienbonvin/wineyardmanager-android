package com.luca.flavien.wineyardmanager.activity_classes;

import android.location.Location;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;

import java.util.ArrayList;
import java.util.List;

public class ActivityMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.10);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        List<WineLot> wineLots = MainActivity.wineLotDataSource.getAllWineLots();

        location = MainActivity.getLocation();


        if (location != null) {
            mMap.addMarker(
                    new MarkerOptions().position(
                            new LatLng(
                                    location.getLatitude(),
                                    location.getLongitude()))
                            .title("USER LOCATION")
                            .icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))));
        }
        else
            Toast.makeText(getApplicationContext(), "User position unknown", Toast.LENGTH_SHORT).show();


        for (WineLot w: wineLots) {
            Log.d("ADD LOCATION: ", "Longitude: " + Double.toString(w.getLongitude()) + " Latitude: " + Double.toString(w.getLatitude()));
            MarkerOptions mO = new MarkerOptions().position(
                            new LatLng(w.getLatitude(), w.getLongitude())).title(w.getName());
            builder.include(mO.getPosition());
            mMap.addMarker(mO);

        }

        LatLngBounds bounds = builder.build();
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
        mMap.animateCamera(cu);
    }
}
