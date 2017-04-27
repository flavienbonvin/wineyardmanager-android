package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.Worker;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

public class ActivityEmployeeAdd extends AppCompatActivity {

    private EditText editTextLastName;
    private EditText editTextFirstName;
    private EditText editTextMail;
    private EditText editTextPhone;

    private Worker worker;
    private boolean hasIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);

        hasIntent = false;
        initEditText();
        checkIntent();


        FloatingActionButton floatingActionButton = (FloatingActionButton)
                findViewById(R.id.fab_confirm_add_employee);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntries()){

                    Worker workerTemp;

                    if (hasIntent){
                        workerTemp = worker;
                        setWorkerFileds(workerTemp);
                        MainActivity.workerDataSource.updateWorker(workerTemp);
                    }
                    else{
                        workerTemp = new Worker();
                        setWorkerFileds(workerTemp);
                        MainActivity.workerDataSource.createWorker(workerTemp);
                    }
                    finish();
                }
            }
        });
    }

    private void setWorkerFileds(Worker workerToSet){
        workerToSet.setFirstName(editTextFirstName.getText().toString());
        workerToSet.setLastName(editTextLastName.getText().toString());
        workerToSet.setMail(editTextMail.getText().toString());
        workerToSet.setPhone(editTextPhone.getText().toString());
    }

    private  void checkIntent(){
        Intent intent = getIntent();

        if(intent.hasExtra("Worker")){
            worker = (Worker)intent.getSerializableExtra("Worker");
            setTitle(getString(R.string.edit)+ " " + worker.getLastName() + " " + worker.getFirstName());

            hasIntent = true;
            setEdit();
        }
        else {
            setTitle(getString(R.string.add_new_worker));
        }
    }

    private void setEdit(){
        editTextFirstName.setText(worker.getFirstName());
        editTextLastName.setText(worker.getLastName());
        editTextMail.setText(worker.getMail());
        editTextPhone.setText(worker.getPhone());
    }

    private void initEditText(){
        editTextFirstName = (EditText)findViewById(R.id.edit_name);
        editTextLastName = (EditText)findViewById(R.id.edit_lastname);
        editTextMail = (EditText)findViewById(R.id.edit_mail);
        editTextPhone = (EditText)findViewById(R.id.edit_phone);
    }

    private boolean checkEntries(){
        if (editTextFirstName.getText().toString().matches("")){
            Toast.makeText(this, R.string.problem_add_worker_firstname, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (editTextLastName.getText().toString().matches("")){
            Toast.makeText(this, R.string.problem_add_worker_lastname , Toast.LENGTH_LONG).show();
            return false;
        }
        if (editTextMail.getText().toString().matches("")){
            Toast.makeText(this, R.string.problem_add_worker_main , Toast.LENGTH_LONG).show();
            return false;
        }
        if (editTextPhone.getText().toString().matches("")){
            Toast.makeText(this, R.string.problem_add_location_phone, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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
