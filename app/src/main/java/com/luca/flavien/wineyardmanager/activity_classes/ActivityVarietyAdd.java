package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.luca.flavien.wineyardmanager.cloud.CloudManager;
import com.luca.flavien.wineyardmanager.cloud.WineVarietyAsyncTask;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: activity_classes
 *
 * Description: Allow the creation and edit of a variety
 */

public class ActivityVarietyAdd extends AppCompatActivity {

    private EditText editTextVariety;

    private WineVariety wineVariety;
    private boolean hasIntent;

    private FloatingActionButton floatingActionButtonDelete;

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

                    CloudManager.sendWineVariety();

                    finish();
                }
                else{
                    editTextVariety.setError(getString(R.string.vine_variety_empty));
                }
            }
        });

        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.wineVarietyDataSource.deleteWineVariety(wineVariety.getId());
                finish();
            }
        });
    }

    /*
     * If we want to edit a vine variety, we put an Intent in the activity creation,
     * we test here if this intent exist.
     *
     * If an Intent exist, we fill the fields with the information of the vine variety in the Intent
     */
    private  void checkIntent(){
        Intent intent = getIntent();

        if(intent.hasExtra("variety")){
            wineVariety = (WineVariety)intent.getSerializableExtra("variety");

            setTitle(getString(R.string.modify) + " " + wineVariety.getName());

            hasIntent = true;
            floatingActionButtonDelete.setVisibility(View.VISIBLE);

            setEdit();
        }
        else {
            setTitle(getString(R.string.add_new_variety));
        }
    }

    /*
     * Set the content of the editText with the information of the vine variety we passed in Intent
     * Allow the user to know the different information
     */
    private void setEdit(){
        editTextVariety.setText(wineVariety.getName());
    }


    private void initEditText(){
        editTextVariety = (EditText)findViewById(R.id.edit_variety_name);

        floatingActionButtonDelete = (FloatingActionButton)findViewById(R.id.fab_delete_vine_variety);
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
