package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.db.object.Worker;

public class ActivityVarietyAdd extends AppCompatActivity {

    private EditText editTextVariety;

    private WineVariety wineVariety;
    private boolean hasIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_variety_add);

        hasIntent = false;
        initEditText();
        checkIntent();


        final FloatingActionButton floatingActionButton =
                (FloatingActionButton)findViewById(R.id.fab_confirm_variety);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editTextVariety.getText().toString().matches("")){

                    WineVariety wineVarietyTemp;

                    if(hasIntent){
                        wineVarietyTemp = wineVariety;
                        wineVarietyTemp.setName(editTextVariety.getText().toString());
                        MainActivity.wineVarietyDataSource.updateWineVariety(wineVarietyTemp);
                    }
                    else{
                        wineVarietyTemp = new WineVariety();
                        wineVarietyTemp.setName(editTextVariety.getText().toString());
                        MainActivity.wineVarietyDataSource.createWineVariety(wineVarietyTemp);
                    }

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

    private  void checkIntent(){
        Intent intent = getIntent();

        if(intent.hasExtra("variety")){
            wineVariety = (WineVariety)intent.getSerializableExtra("variety");

            setTitle(getString(R.string.modify) + " " + wineVariety.getName());
            hasIntent = true;

            setEdit();
        }
        else {
            setTitle(getString(R.string.add_new_variety));
        }
    }

    private void setEdit(){
        editTextVariety.setText(wineVariety.getName());
    }

    private void initEditText(){
        editTextVariety = (EditText)findViewById(R.id.edit_variety_name);
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
