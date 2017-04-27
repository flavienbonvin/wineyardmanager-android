package com.luca.flavien.wineyardmanager.activity_classes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.Worker;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.Calendar;
import java.util.List;

public class ActivityWorkAdd extends AppCompatActivity {

    private Spinner spinnerWorker;
    private Spinner spinnerWineLot;

    private EditText editTextAction;
    private EditText editTextDeadline;
    private DatePickerDialog datePickerDialog;

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


        editTextDeadline = (EditText)findViewById(R.id.edit_deadline);
        editTextDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(ActivityWorkAdd.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                editTextDeadline.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
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
        if (editTextAction.getText().toString().isEmpty()){
            editTextAction.setError(getString(R.string.action_empty));
            return false;
        }
        if (editTextDeadline.getText().toString().isEmpty()){
            editTextDeadline.setError(getString(R.string.deadline_empty));
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
