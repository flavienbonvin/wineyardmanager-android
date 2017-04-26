package com.luca.flavien.wineyardmanager.activity_classes;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

public class ActivityVarietyAdd extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variety_add);

        final FloatingActionButton floatingActionButton =
                (FloatingActionButton)findViewById(R.id.fab_confirm_variety);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textViewVariety = (EditText)findViewById(R.id.edit_variety_name);
                if(!textViewVariety.getText().toString().matches("")){
                    WineVariety wineVariety = new WineVariety();
                    wineVariety.setName(textViewVariety.getText().toString());

                    MainActivity.wineVarietyDataSource.createWineVariety(wineVariety);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.problem_add_location_variety),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
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
