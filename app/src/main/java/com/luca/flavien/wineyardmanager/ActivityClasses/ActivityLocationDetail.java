package com.luca.flavien.wineyardmanager.ActivityClasses;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.luca.flavien.wineyardmanager.DB.Object.WineLot;
import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien on 24.04.2017.
 */

public class ActivityLocationDetail extends AppCompatActivity {

    private WineLot wineLot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        wineLot = (WineLot) getIntent().getSerializableExtra("winelot");
        setTextView(wineLot);
    }

    private void setTextView(WineLot wineLot){
        TextView textViewWineVariety = (TextView)findViewById(R.id.winevariety);
        TextView textViewnumberWineStock = (TextView)findViewById(R.id.number_of_wine_stock);
        TextView textViewSurface = (TextView)findViewById(R.id.surface);
        TextView textViewOrientation = (TextView)findViewById(R.id.orientation);

        setTitle(wineLot.getName());

        textViewWineVariety.setText(wineLot.getWineVariety().getName());
        textViewnumberWineStock.setText(Integer.toString(wineLot.getNumberWineStock()) + " " + getString(R.string.vines));
        textViewSurface.setText(Float.toString(wineLot.getSurface()) + " " + getString(R.string.square_meters));
        textViewOrientation.setText(wineLot.getOrientation().getName());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.onBackPressed();
            return true;
        }
        return true;
    }
}