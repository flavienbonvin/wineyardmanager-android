package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.Orientation;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.db.object.Worker;

import java.util.List;

public class ActivityLocationAdd extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextNumber;
    private EditText editTextSurface;

    private Spinner spinnerOrientation;
    private Spinner spinnerVariety;

    private WineLot wineLot;
    private boolean hasIntent;

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
                    finish();
                }
            }
        });
    }

    private void setWineLotField(){
        Orientation orientation = (Orientation) spinnerOrientation.getSelectedItem();
        WineVariety wineVariety = (WineVariety) spinnerVariety.getSelectedItem();

        Log.d("CREATE YINEYARD: ", wineVariety.getId() + " name: " + wineVariety.getName());

        wineLot.setWineVariety(wineVariety);
        wineLot.setOrientationid(orientation.getId());
        wineLot.setName(editTextName.getText().toString());
        wineLot.setNumberWineStock(Integer.parseInt(editTextNumber.getText().toString()));
        wineLot.setSurface(Float.parseFloat(editTextSurface.getText().toString()));
    }

    private  void checkIntent(){
        Intent intent = getIntent();

        if(intent.hasExtra("vinelot")){
            wineLot = (WineLot) intent.getSerializableExtra("vinelot");
            setTitle(getString(R.string.edit)+ " " + wineLot.getName());

            hasIntent = true;
            setEdit();
        }
        else {
            setTitle(getString(R.string.add_new_vinelot));
        }
    }

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

    private void initObjects(){
        spinnerOrientation = (Spinner) findViewById(R.id.spinner_orientation);
        spinnerVariety = (Spinner) findViewById(R.id.spinner_variety);

        editTextName = (EditText)findViewById(R.id.edit_vineyard_name);
        editTextNumber = (EditText)findViewById(R.id.edit_number_vine_stock);
        editTextSurface = (EditText)findViewById(R.id.edit_size);
    }

    private void updateSpinner(){
        ArrayAdapter<Orientation> adapterOrientation = new ArrayAdapter<>
                (this, R.layout.row_simple, MainActivity.orientationList);
        spinnerOrientation.setAdapter(adapterOrientation);

        List<WineVariety> varietyList = MainActivity.wineVarietyDataSource.getAllWineVarieties();
        ArrayAdapter<WineVariety> adapterVariety = new ArrayAdapter<>
                (this, R.layout.row_simple, varietyList);
        spinnerVariety.setAdapter(adapterVariety);
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
