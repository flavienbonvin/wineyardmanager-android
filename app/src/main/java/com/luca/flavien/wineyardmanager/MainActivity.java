package com.luca.flavien.wineyardmanager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.luca.flavien.wineyardmanager.activity_classes.ActivityMap;
import com.luca.flavien.wineyardmanager.cloud.JobAsyncTask;
import com.luca.flavien.wineyardmanager.cloud.WineLotAsyncTask;
import com.luca.flavien.wineyardmanager.cloud.WineVarietyAsyncTask;
import com.luca.flavien.wineyardmanager.cloud.WorkerAsyncTask;
import com.luca.flavien.wineyardmanager.db.adapter.JobDataSource;
import com.luca.flavien.wineyardmanager.db.adapter.WineLotDataSource;
import com.luca.flavien.wineyardmanager.db.adapter.WineVarietyDataSource;
import com.luca.flavien.wineyardmanager.db.adapter.WorkerDataSource;
import com.luca.flavien.wineyardmanager.db.object.Orientation;
import com.luca.flavien.wineyardmanager.entities.jobApi.model.Job;
import com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineLot;
import com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety;
import com.luca.flavien.wineyardmanager.entities.workerApi.model.Worker;
import com.luca.flavien.wineyardmanager.fragment_classes.FragEmployee;
import com.luca.flavien.wineyardmanager.fragment_classes.FragInformations;
import com.luca.flavien.wineyardmanager.fragment_classes.FragLocationList;
import com.luca.flavien.wineyardmanager.fragment_classes.FragOrientation;
import com.luca.flavien.wineyardmanager.fragment_classes.FragSettings;
import com.luca.flavien.wineyardmanager.fragment_classes.FragVineVariety;
import com.luca.flavien.wineyardmanager.fragment_classes.FragWork;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: Main
 *
 * Description: MainActivity
 */

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fm;
    private FragmentTransaction ft;

    public static JobDataSource jobDataSource;
    public static WineLotDataSource wineLotDataSource;
    public static WineVarietyDataSource wineVarietyDataSource;
    public static WorkerDataSource workerDataSource;


    private LocationManager locationManager;
    private static Location location;


    public static List<String> languageList;
    public static List<Orientation> orientationList;

    public static int languagePosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAppBar);
        setSupportActionBar(toolbar);

        //Create the adapter for the database
        Context context = getApplicationContext();
        jobDataSource = new JobDataSource(context);
        wineLotDataSource = new WineLotDataSource(context);
        wineVarietyDataSource = new WineVarietyDataSource(context);
        workerDataSource = new WorkerDataSource(context);

        testRecieveCloud();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        this.setTitle(R.string.works);
        ft.replace(R.id.content_layout, new FragWork()).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Check if the app has all the permissions it needs;
        checkPermissions();


        //Create all the orientation possible, this was had coded because there isn't
        //a need to modify them
        initiateOrientations();


        //Create all the language possible
        initiateLanguage();

        //Set the language
        languagePosition = setLanguage();


        //Get the location of the user
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        getUserLocation();
    }

    private void checkPermissions(){
        int PERMISSION_ALL = 1;


        //List of all the permissions needed by the app
        String[] permissions = new String[]{
                Manifest.permission.WRITE_CALENDAR,
                Manifest.permission.SEND_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};


        if(!hasPermissions(this, permissions)){
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_ALL);
        }
    }


    private static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (id){
            case (R.id.nav_map):
                Intent intent = new Intent(this, ActivityMap.class);
                startActivity(intent);
                break;
            case (R.id.nav_location):
                this.setTitle(R.string.location);
                ft.replace(R.id.content_layout, new FragLocationList()).commit();
                break;
            case (R.id.nav_work):
                this.setTitle(getString(R.string.works));
                ft.replace(R.id.content_layout, new FragWork()).commit();
                break;
            case (R.id.nav_employee):
                this.setTitle(getString(R.string.employee));
                ft.replace(R.id.content_layout, new FragEmployee()).commit();
                break;
            case (R.id.nav_setting):
                this.setTitle(getString(R.string.settings));
                ft.replace(R.id.content_layout, new FragSettings()).commit();
                break;
            case (R.id.nav_vine_variety):
                this.setTitle(getString(R.string.wine_variety));
                ft.replace(R.id.content_layout, new FragVineVariety()).commit();
                break;
            case (R.id.nav_manage_orientation):
                this.setTitle(getString(R.string.Orientation));
                ft.replace(R.id.content_layout, new FragOrientation()).commit();
                break;
            case (R.id.nav_informations):
                this.setTitle("About us");
                ft.replace(R.id.content_layout, new FragInformations()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return false;
    }


    private void initiateOrientations(){
        Orientation s = new Orientation(0, getString(R.string.south));
        Orientation se = new Orientation(1, getString(R.string.south) + " " + getString(R.string.east));
        Orientation sw = new Orientation(2, getString(R.string.south) + " " + getString(R.string.west));

        Orientation n = new Orientation(3, getString(R.string.north));
        Orientation ne = new Orientation(4, getString(R.string.north) + " " + getString(R.string.east));
        Orientation nw = new Orientation(5, getString(R.string.north) + " " + getString(R.string.west));

        Orientation e = new Orientation(6, getString(R.string.east));
        Orientation w = new Orientation(7, getString(R.string.west));

        orientationList = new ArrayList<>();

        orientationList.add(s);
        orientationList.add(se);
        orientationList.add(sw);

        orientationList.add(n);
        orientationList.add(ne);
        orientationList.add(nw);

        orientationList.add(e);
        orientationList.add(w);
    }


    private void initiateLanguage(){
        String german = getString(R.string.language_german);
        String french = getString(R.string.language_french);
        String english = getString(R.string.language_english);
        String italian = getString(R.string.language_italian);


        languageList = new ArrayList<>();

        languageList.add(german);
        languageList.add(french);
        languageList.add(english);
        languageList.add(italian);

    }


    private int setLanguage(){
        String language = Locale.getDefault().getDisplayLanguage();
        if(language.contains("Deutsch")){
            return 0 ;
        }

        if(language.contains("français")){
            return 1 ;
        }

        if(language.contains("italiano")){
            return 3 ;
        }
        return 2 ;
    }



    private void getUserLocation(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2* 60 * 1000, 250, locationListenerNetwork);
    }


    private final LocationListener locationListenerNetwork = new LocationListener() {
        public void onLocationChanged(Location location) {
            MainActivity.location = location;
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    };

    public static Location getLocation() {
        return location;
    }


    //TODO delete once all tests are finished
    private void testSendCloud(){
        WineVariety wineVariety = new WineVariety();
        wineVariety.setId((long) 1);
        wineVariety.setName("Variety 1");

        Worker worker = new Worker();
        worker.setId((long)1);
        worker.setFirstName("Flavien");
        worker.setLastName("Cento");
        worker.setMail("cento@bonvin.ch");
        worker.setPhone("88");


        com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineVariety wineVariety1
                = new com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineVariety();
        wineVariety1.setId((long) 2);
        wineVariety1.setName("Variety 2");

        WineLot wineLot = new WineLot();
        wineLot.setId((long) 1);
        wineLot.setName("Parcelle 1");
        wineLot.setLatitude(10.0d);
        wineLot.setLongitude(10.0d);
        wineLot.setNumberWineStock(250);
        wineLot.setSurface(100f);
        wineLot.setOrientationid(1);
        wineLot.setWineVariety(wineVariety1);


        com.luca.flavien.wineyardmanager.entities.jobApi.model.WineVariety wineVariety2
                = new com.luca.flavien.wineyardmanager.entities.jobApi.model.WineVariety();
        wineVariety2.setId((long) 3);
        wineVariety2.setName("Variety 3");
        com.luca.flavien.wineyardmanager.entities.jobApi.model.WineLot wineLot1
                = new com.luca.flavien.wineyardmanager.entities.jobApi.model.WineLot();
        wineLot1.setId((long) 1);
        wineLot1.setName("Parcelle 2");
        wineLot1.setLatitude(10.0d);
        wineLot1.setLongitude(10.0d);
        wineLot1.setNumberWineStock(250);
        wineLot1.setSurface(100f);
        wineLot1.setOrientationid(1);
        wineLot1.setWineVariety(wineVariety2);

        com.luca.flavien.wineyardmanager.entities.jobApi.model.Worker worker1
                = new com.luca.flavien.wineyardmanager.entities.jobApi.model.Worker();
        worker1.setId((long)2);
        worker1.setFirstName("Luca");
        worker1.setLastName("Bonvin");
        worker1.setMail("cento@bonvin.ch");
        worker1.setPhone("55");

        Job job = new Job();
        job.setId((long) 1);
        job.setDeadline("10.10.10");
        job.setDescription("Tailler");
        job.setWinelot(wineLot1);
        job.setWorker(worker1);


        new WineVarietyAsyncTask(wineVariety).execute();
        new WorkerAsyncTask(worker).execute();
        new WineLotAsyncTask(wineLot).execute();
        new JobAsyncTask(job).execute();
    }

    private void testRecieveCloud(){
        new WineVarietyAsyncTask().execute();
        new WorkerAsyncTask().execute();
        new WineLotAsyncTask().execute();
        new JobAsyncTask().execute();
    }
}