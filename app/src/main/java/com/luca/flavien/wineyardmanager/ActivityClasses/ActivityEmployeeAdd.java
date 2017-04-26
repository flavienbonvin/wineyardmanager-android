package com.luca.flavien.wineyardmanager.ActivityClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.luca.flavien.wineyardmanager.R;

public class ActivityEmployeeAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);
        setTitle("Add an employee");
    }
}
