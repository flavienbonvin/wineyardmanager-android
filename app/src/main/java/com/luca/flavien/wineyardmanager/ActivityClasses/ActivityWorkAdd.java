package com.luca.flavien.wineyardmanager.ActivityClasses;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.luca.flavien.wineyardmanager.DB.Object.Worker;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.List;

public class ActivityWorkAdd extends AppCompatActivity {

    private Spinner spinnerWorker;
    private Spinner spinnerWineLot;

    private EditText editTextAction;
    private EditText editTextDeadline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_add);
        setTitle("Add a work");

        initObjects();
        updateSpinner();

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateSpinner();
    }

    private void initObjects(){
        spinnerWorker = (Spinner)findViewById(R.id.spinner_worker);

        editTextAction = (EditText)findViewById(R.id.edit_action);
        editTextDeadline = (EditText)findViewById(R.id.edit_deadline);
    }

    private void updateSpinner(){
        List<Worker> workerList = MainActivity.workerDataSource.getAllWorkers();
        ArrayAdapter<Worker> adapterWorker = new ArrayAdapter<Worker>
                (this, R.layout.row_simple, workerList);
        spinnerWorker.setAdapter(adapterWorker);
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
