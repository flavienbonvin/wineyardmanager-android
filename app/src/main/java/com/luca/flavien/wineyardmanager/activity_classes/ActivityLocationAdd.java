package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.cloud.CloudManager;
import com.luca.flavien.wineyardmanager.cloud.WineLotAsyncTask;
import com.luca.flavien.wineyardmanager.cloud.WineVarietyAsyncTask;
import com.luca.flavien.wineyardmanager.db.object.Orientation;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;

import java.util.List;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: activity_classes
 *
 * Description: Allow the creation and edit of a location
 */

public class ActivityLocationAdd extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextNumber;
    private EditText editTextSurface;

    private Spinner spinnerOrientation;
    private Spinner spinnerVariety;

    private TextView textLocation;
    private Location location;

    private WineLot wineLot;
    private boolean hasIntent;


    private FloatingActionButton floatingActionButtonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_add);

        initObjects();
        updateSpinner();

        checkIntent();

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_confirm_location);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEntries()){
                    if (hasIntent){
                        setWineLotField();
                        MainActivity.wineLotDataSource.updateWineLot(wineLot);
                    }
                    else {
                        wineLot = new WineLot();
                        setWineLotField();
                        MainActivity.wineLotDataSource.createWineLot(wineLot);
                    }

                    CloudManager.sendOneWineLot(wineLot);

                    finish();
                }
            }
        });

        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.wineLotDataSource.deleteWineLot(wineLot.getId());
                new WineLotAsyncTask(((long)wineLot.getId())).execute();
                finish();
            }
        });
    }

    /*
    * Create the wineField and fill the information with the different information in the editText
    */
    private void setWineLotField(){
        Orientation orientation = (Orientation) spinnerOrientation.getSelectedItem();
        WineVariety wineVariety = (WineVariety) spinnerVariety.getSelectedItem();


        Log.d("CREATE VINEYARD: ", wineVariety.getId() +
                " name: " + wineVariety.getName());

        wineLot.setWineVariety(wineVariety);
        wineLot.setOrientationid(orientation.getId());
        wineLot.setName(editTextName.getText().toString());
        wineLot.setNumberWineStock(Integer.parseInt(editTextNumber.getText().toString()));
        wineLot.setSurface(Float.parseFloat(editTextSurface.getText().toString()));

        //Test if the location is null to avoid overriding the information of the location
        if (location != null) {
            wineLot.setLongitude(location.getLongitude());
            wineLot.setLatitude(location.getLatitude());
        }
    }

    /*
     * If we want to edit a wineLot, we put an Intent in the activity creation,
     * we test here if this intent exist.
     *
     * If an Intent exist, we fill the fields with the information of the employee in the Intent
     */
    private  void checkIntent(){
        Intent intent = getIntent();

        if(intent.hasExtra("vine_lot")){
            wineLot = (WineLot) intent.getSerializableExtra("vine_lot");
            setTitle(getString(R.string.edit)+ " " + wineLot.getName());

            hasIntent = true;

            floatingActionButtonDelete.setVisibility(View.VISIBLE);

            setEdit();
        }
        else {
            setTitle(getString(R.string.add_new_vine_lot));
        }
    }

    /*
     * Check if there is a location in the main and set the textField with the information of it
     */
    public void locationManagement(View view){
        location = MainActivity.getLocation();

        if (location != null) {
            textLocation.setText(getString(R.string.location) + ": \n" + getString(R.string.longitude) + ": " + location.getLongitude() + "\n" + getString(R.string.latitude) + ": " + location.getLatitude());
        }
    }

    /*
    * Set the content of the editText with the information of the wineLot we passed in Intent
    * Allow the user to know the different information
    */
    private void setEdit(){
        editTextName.setText(wineLot.getName());
        editTextNumber.setText(Integer.toString(wineLot.getNumberWineStock()));
        editTextSurface.setText(Float.toString(wineLot.getSurface()));

        spinnerOrientation.setSelection(wineLot.getOrientationid());
    }


    private boolean checkEntries(){
        if (editTextName.getText().toString().trim().isEmpty()){
            editTextName.setError(getString(R.string.location_name_empty));
            return false;
        }
        if (editTextNumber.getText().toString().trim().isEmpty()){
            editTextNumber.setError(getString(R.string.stocks_empty));
            return false;
        }
        if (editTextSurface.getText().toString().trim().isEmpty()){
            editTextSurface.setError(getString(R.string.surface_empty));
            return false;
        }
        return true;
    }

    /*
     * Init the objects of the layout
     */
    private void initObjects(){
        spinnerOrientation = (Spinner) findViewById(R.id.spinner_orientation);
        spinnerVariety = (Spinner) findViewById(R.id.spinner_variety);

        editTextName = (EditText)findViewById(R.id.edit_vineyard_name);
        editTextNumber = (EditText)findViewById(R.id.edit_number_vine_stock);
        editTextSurface = (EditText)findViewById(R.id.edit_size);

        floatingActionButtonDelete = (FloatingActionButton)findViewById(R.id.fab_delete_location);

        textLocation = (TextView)findViewById(R.id.tw_user_location);
    }


    private void updateSpinner(){
        ArrayAdapter<Orientation> adapterOrientation = new ArrayAdapter<>
                (this, R.layout.row_simple, MainActivity.orientationList);
        spinnerOrientation.setAdapter(adapterOrientation);

        List<WineVariety> varietyList = MainActivity.wineVarietyDataSource.getAllWineVarieties();
        ArrayAdapter<WineVariety> adapterVariety = new ArrayAdapter<>
                (this, R.layout.row_simple, varietyList);
        spinnerVariety.setAdapter(adapterVariety);

        if (spinnerVariety == null ||  spinnerVariety.getSelectedItem() == null){
            Toast.makeText(getApplicationContext(),
                    R.string.create_a_vinevariety,
                    Toast.LENGTH_SHORT).show();
            finish();
        }
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
