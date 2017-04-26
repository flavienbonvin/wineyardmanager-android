package com.luca.flavien.wineyardmanager.ActivityClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luca.flavien.wineyardmanager.R;

public class ActivityWorkAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_add);
        setTitle("Add a work");
    }
}
