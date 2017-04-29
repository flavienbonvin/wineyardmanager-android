package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import java.util.List;

public class ActivityMap extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
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
        GoogleMap mMap = googleMap;
        mMap.setMaxZoomPreference(20);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.1);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        final List<WineLot> wineLots = MainActivity.wineLotDataSource.getAllWineLots();

        Location location = MainActivity.getLocation();


        if (location != null) {

            MarkerOptions mO = new MarkerOptions().position(
                    new LatLng(
                            location.getLatitude(),
                            location.getLongitude()))
                    .title(getString(R.string.user_location))
                    .icon((BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            builder.include(mO.getPosition());
            mMap.addMarker(mO);

            LatLngBounds bounds = builder.build();
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
            mMap.animateCamera(cu);

        }
        else
            Toast.makeText(getApplicationContext(), R.string.user_position_unknown, Toast.LENGTH_SHORT).show();



        if (wineLots != null) {
            for (WineLot w : wineLots) {
                Log.d("ADD LOCATION: ", "Longitude: " + Double.toString(w.getLongitude()) + " Latitude: " + Double.toString(w.getLatitude()));


                MarkerOptions mO = new MarkerOptions().position(
                        new LatLng(w.getLatitude(), w.getLongitude())).title(w.getName());

                builder.include(mO.getPosition());
                mMap.addMarker(mO);

                LatLngBounds bounds = builder.build();
                CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);
                mMap.animateCamera(cu);
            }
        } else{
            Toast.makeText(getApplicationContext(), R.string.you_have_no_winelot, Toast.LENGTH_SHORT).show();
        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (!marker.getTitle().equals(getString(R.string.user_location))) {
                    Intent intent = new Intent(getApplicationContext(), ActivityLocationDetail.class);

                    for (WineLot w : wineLots != null ? wineLots : null) {
                        if (w.getName().equals(marker.getTitle())) {
                            intent.putExtra("winelot", w);
                            startActivity(intent);
                            break;
                        }
                    }
                }
                return false;
            }
        });
    }
}
