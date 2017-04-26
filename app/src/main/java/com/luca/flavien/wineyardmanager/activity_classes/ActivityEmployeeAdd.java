package com.luca.flavien.wineyardmanager.activity_classes;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);
        initEditText();

        FloatingActionButton floatingActionButton = (FloatingActionButton)
                findViewById(R.id.fab_confirm_add_employee);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntries()){
                    Worker worker = new Worker();
                    worker.setFirstName(editTextFirstName.getText().toString());
                    worker.setLastName(editTextLastName.getText().toString());
                    worker.setMail(editTextMail.getText().toString());
                    worker.setPhone(editTextPhone.getText().toString());

                    MainActivity.workerDataSource.createWorker(worker);
                    finish();
                }
            }
        });
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
