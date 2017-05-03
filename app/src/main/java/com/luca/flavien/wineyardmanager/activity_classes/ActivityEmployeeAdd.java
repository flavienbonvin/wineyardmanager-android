package com.luca.flavien.wineyardmanager.activity_classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.db.object.Worker;


/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: activity_classes
 *
 * Description: Allow the creation and edit of employee
 */

public class ActivityEmployeeAdd extends AppCompatActivity {

    private EditText editTextLastName;
    private EditText editTextFirstName;
    private EditText editTextMail;
    private EditText editTextPhone;

    private Worker worker;
    private boolean hasIntent;

    private FloatingActionButton floatingActionButtonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_add);

        hasIntent = false;
        initEditText();
        checkIntent();

        final FloatingActionButton floatingActionButton = (FloatingActionButton)
                findViewById(R.id.fab_confirm_add_employee);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEntries()) {

                    Worker workerTemp;

                    if (hasIntent) {
                        workerTemp = worker;
                        setWorkerFiled(workerTemp);
                        MainActivity.workerDataSource.updateWorker(workerTemp);
                    } else {
                        workerTemp = new Worker();
                        setWorkerFiled(workerTemp);
                        MainActivity.workerDataSource.createWorker(workerTemp);
                    }
                    finish();
                }
            }
        });

        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.workerDataSource.deleteWorker(worker.getId());
                finish();
            }
        });
    }

    /*
     * Create the worker and fill the information with the different information in the editText
     */
    private void setWorkerFiled(Worker workerToSet) {
        workerToSet.setFirstName(editTextFirstName.getText().toString());
        workerToSet.setLastName(editTextLastName.getText().toString());
        workerToSet.setMail(editTextMail.getText().toString());
        workerToSet.setPhone(editTextPhone.getText().toString());
    }

    /*
     * If we want to edit a employee, we put an Intent in the activity creation,
     * we test here if this intent exist.
     *
     * If an Intent exist, we fill the fields with the information of the employee in the Intent
     */
    private void checkIntent() {
        Intent intent = getIntent();

        if (intent.hasExtra("Worker")) {
            worker = (Worker) intent.getSerializableExtra("Worker");
            setTitle(getString(R.string.edit) + " " + worker.getLastName() + " " + worker.getFirstName());

            hasIntent = true;

            floatingActionButtonDelete.setVisibility(View.VISIBLE);

            setEdit();
        } else {
            setTitle(getString(R.string.add_new_worker));
        }
    }


    /*
     * Set the content of the editText with the information of the employee we passed in Intent
     * Allow the user to know the different informations
     */
    private void setEdit() {
        editTextFirstName.setText(worker.getFirstName());
        editTextLastName.setText(worker.getLastName());
        editTextMail.setText(worker.getMail());
        editTextPhone.setText(worker.getPhone());
    }


    private void initEditText() {
        editTextFirstName = (EditText) findViewById(R.id.edit_name);
        editTextLastName = (EditText) findViewById(R.id.edit_lastname);
        editTextMail = (EditText) findViewById(R.id.edit_mail);
        editTextPhone = (EditText) findViewById(R.id.edit_phone);

        floatingActionButtonDelete = (FloatingActionButton) findViewById(R.id.fab_delete_worker);
    }


    private boolean checkEntries() {
        if (editTextLastName.getText().toString().trim().isEmpty()) {
            editTextLastName.setError(getString(R.string.lastname_empty));
            return false;
        }
        if (editTextFirstName.getText().toString().trim().isEmpty()) {
            editTextFirstName.setError(getString(R.string.fistname_empty));
            return false;
        }
        if (!isValidEmail(editTextMail.getText())) {
            editTextMail.setError(getString(R.string.email_not_valid));
            return false;
        }
        if (!isValidMobile(editTextPhone.getText())) {
            editTextPhone.setError(getString(R.string.phone_not_valid));
            return false;
        }
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        return true;
    }

    /*
     * Test if the mobile phone and the email is in a valid format
     */
    private boolean isValidMobile(CharSequence phone) {
        if (phone.toString().trim().isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }
    private static boolean isValidEmail(CharSequence mail) {
        if (mail.toString().trim().isEmpty()) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches();
        }
    }
}
