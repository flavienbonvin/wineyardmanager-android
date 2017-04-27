package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.db.object.Worker;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.List;

public class ActivityWorkAdd extends AppCompatActivity {

    private Spinner spinnerWorker;
    private Spinner spinnerWineLot;

    private EditText editTextAction;
    private EditText editTextDeadline;

    private Job job;
    private boolean hasIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_add);
        setTitle("Add a work");

        hasIntent = false;

        initObjects();
        updateSpinner();

        checkIntent();

        FloatingActionButton floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_confirm_location);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEntries()){


                    if (hasIntent){
                        setJobField();
                        MainActivity.jobDataSource.updateJob(job);
                    }
                    else {
                        job = new Job();
                        setJobField();
                        MainActivity.jobDataSource.createJob(job);
                    }
                    finish();
                }
            }
        });
    }

    private void setJobField(){
        Worker worker = (Worker)spinnerWorker.getSelectedItem();
        WineLot wineLot = (WineLot)spinnerWineLot.getSelectedItem();

        job.setWinelot(wineLot);
        job.setDeadline(editTextDeadline.getText().toString());
        job.setDescription(editTextAction.getText().toString());
        job.setWorker(worker);
    }

    private  void checkIntent(){
        Intent intent = getIntent();

        if(intent.hasExtra("job")){
            job = (Job) intent.getSerializableExtra("job");

            setTitle(getString(R.string.modify));
            hasIntent = true;

            setEdit();
        }
        else {
            setTitle(getString(R.string.add_new_job));
        }
    }

    private void setEdit(){
        editTextAction.setText(job.getDescription());
        editTextDeadline.setText(job.getDeadline());

        spinnerWineLot.setSelection(job.getWinelot().getId());
        spinnerWorker.setSelection(job.getWorker().getId());
    }



    @Override
    protected void onResume() {
        super.onResume();
        updateSpinner();
    }

    private boolean checkEntries(){
        if (editTextAction.getText().toString().matches("")){
            Toast.makeText(this, R.string.problem_add_work_action, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextDeadline.getText().toString().matches("")){
            Toast.makeText(this, R.string.problem_add_work_deadline, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void initObjects(){
        spinnerWorker = (Spinner)findViewById(R.id.spinner_worker);
        spinnerWineLot = (Spinner)findViewById(R.id.spinner_winelot);

        editTextAction = (EditText)findViewById(R.id.edit_action);
        editTextDeadline = (EditText)findViewById(R.id.edit_deadline);
    }

    private void updateSpinner(){
        List<Worker> workerList = MainActivity.workerDataSource.getAllWorkers();
        ArrayAdapter<Worker> adapterWorker = new ArrayAdapter<>
                (this, R.layout.row_simple, workerList);
        spinnerWorker.setAdapter(adapterWorker);

        List<WineLot> wineLotList = MainActivity.wineLotDataSource.getAllWineLots();
        ArrayAdapter<WineLot> adapterWineLot = new ArrayAdapter<>
                (this, R.layout.row_simple, wineLotList);
        spinnerWineLot.setAdapter(adapterWineLot);
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
