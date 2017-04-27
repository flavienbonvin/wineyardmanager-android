package com.luca.flavien.wineyardmanager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.luca.flavien.wineyardmanager.activity_classes.AndroidDatabaseManager;
import com.luca.flavien.wineyardmanager.db.adapter.JobDataSource;
import com.luca.flavien.wineyardmanager.db.adapter.WineLotDataSource;
import com.luca.flavien.wineyardmanager.db.adapter.WineVarietyDataSource;
import com.luca.flavien.wineyardmanager.db.adapter.WorkerDataSource;
import com.luca.flavien.wineyardmanager.db.object.Orientation;
import com.luca.flavien.wineyardmanager.fragment_classes.FragEmployee;
import com.luca.flavien.wineyardmanager.fragment_classes.FragLocationList;
import com.luca.flavien.wineyardmanager.fragment_classes.FragOrientation;
import com.luca.flavien.wineyardmanager.fragment_classes.FragSettings;
import com.luca.flavien.wineyardmanager.fragment_classes.FragVineVariety;
import com.luca.flavien.wineyardmanager.fragment_classes.FragWork;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fm;
    private FragmentTransaction ft;

    public static JobDataSource jobDataSource;
    public static WineLotDataSource wineLotDataSource;
    public static WineVarietyDataSource wineVarietyDataSource;
    public static WorkerDataSource workerDataSource;

    public static List<Orientation> orientationList;


    /*
        Problems:
        OK Rotation brings back to location fragment
        OK Deadline should have another keyboard or even a calendar
        OK Works only have one line
        - Adapt values of spinners when modifying a object (location, work)
        - Update display instead of going back to the list (location, work)
        - Check the verification of the phone number, swiss number dosn't work
     */

    /*
        To add:
        OK Send message to worker in order to notify the job
        - Translate the app
        - Comments
        OK Edit for
            OK Worker
            OK Work (Add edit button)
            OK Vine lot
            OK Vine variety
        - Delete for
            - Worker
            - Work
            - Vine lot
            - Vine variety
        OK Remove 3 dots
     */

    /*
        Optional:
        OK Make the first letter of a textedit in capital
        - Photo of the worker
        - Photo of the vineyard
        OK Remove orientation table
        - Create calendar envent for the jobs
        - Check input in EditTexts
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAppBar);
        setSupportActionBar(toolbar);

        Context context = getApplicationContext();

        jobDataSource = new JobDataSource(context);
        wineLotDataSource = new WineLotDataSource(context);
        wineVarietyDataSource = new WineVarietyDataSource(context);
        workerDataSource = new WorkerDataSource(context);

        initiateOrientations();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        this.setTitle(R.string.location);
        ft.replace(R.id.content_layout, new FragLocationList()).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (id){
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
                Intent intent = new Intent(this, AndroidDatabaseManager.class);
                startActivity(intent);
                this.setTitle(getString(R.string.settings));
                //ft.replace(R.id.content_layout, new FragSettings()).commit();
                break;
            case (R.id.nav_vine_variety):
                this.setTitle(getString(R.string.wine_variety));
                ft.replace(R.id.content_layout, new FragVineVariety()).commit();
                break;
            case (R.id.nav_manage_orientation):
                this.setTitle(getString(R.string.Orientation));
                ft.replace(R.id.content_layout, new FragOrientation()).commit();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
}
