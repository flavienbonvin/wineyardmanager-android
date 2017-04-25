package com.luca.flavien.wineyardmanager.ActivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.luca.flavien.wineyardmanager.DB.Object.Job;
import com.luca.flavien.wineyardmanager.DB.Object.Worker;
import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien on 24.04.2017.
 */

public class ActivityEmployeeDetail extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Worker passWorker = (Worker) intent.getSerializableExtra("Worker");

        TextView textViewName = (TextView) findViewById(R.id.name);
        TextView textViewPhone = (TextView) findViewById(R.id.phone);
        TextView textViewMail = (TextView) findViewById(R.id.mail);

        textViewName.setText(passWorker.getLastName()+" "+ passWorker.getFirstName());
        textViewPhone.setText(passWorker.getPhone());
        textViewMail.setText(passWorker.getMail());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }
}
