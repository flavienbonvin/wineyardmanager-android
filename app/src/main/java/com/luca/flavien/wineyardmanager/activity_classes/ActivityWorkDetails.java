package com.luca.flavien.wineyardmanager.activity_classes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.MailTo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien on 24.04.2017.
 */

public class ActivityWorkDetails extends AppCompatActivity {

    private FloatingActionButton floatingActionButtonMenu;
    private FloatingActionButton floatingActionButtonMsg;
    private FloatingActionButton floatingActionButtonEdit;
    private FloatingActionButton floatingActionButtonMail;
    private FloatingActionButton floatingActionButtonPhone;

    private Job passJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);

        final Intent intent = getIntent();
        passJob = (Job) intent.getSerializableExtra("job");

        TextView textViewDescription = (TextView) findViewById(R.id.description);
        TextView textViewDeadLine = (TextView) findViewById(R.id.deadLine);
        TextView textViewWineLotName = (TextView) findViewById(R.id.wineLotName);
        TextView textViewWorkerName = (TextView) findViewById(R.id.workerName);

        setTitle(getString(R.string.work_details));
        textViewDescription.setText(passJob.getDescription());
        textViewDeadLine.setText(passJob.getDeadline());
        textViewWineLotName.setText(passJob.getWinelot().getName());
        textViewWorkerName.setText(passJob.getWorker().getLastName() + " " + passJob.getWorker().getFirstName());

        floatingActionButtonMenu = (FloatingActionButton) findViewById(R.id.fabmenu);
        floatingActionButtonMsg = (FloatingActionButton) findViewById(R.id.fab_send_message);
        floatingActionButtonEdit = (FloatingActionButton) findViewById(R.id.fab_edit_work);
        floatingActionButtonMail = (FloatingActionButton) findViewById(R.id.fab_send_mail);
        floatingActionButtonPhone = (FloatingActionButton) findViewById(R.id.fab_call_worker);
        floatingActionButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayHideFAB();
                setFloatingActionButtonListener();
            }
        });
    }

    private void setFloatingActionButtonListener() {
        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(getApplicationContext(), ActivityWorkAdd.class);
                intentEdit.putExtra("job", passJob);
                startActivity(intentEdit);
                finish();
            }
        });
        floatingActionButtonMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = passJob.getWorker().getPhone();
                String msg =
                        getString(R.string.job_to_do) + " " +
                                passJob.getDescription() + " " +
                                getString(R.string.vineyard_name) + ": " + passJob.getWinelot() + " " +
                                getString(R.string.deadline) + ": " + passJob.getDeadline();

                Log.d("SEND SMS: ", phoneNo + " " + msg);

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, msg, null, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                displayHideFAB();
            }
        });
        floatingActionButtonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = passJob.getWorker().getMail();
                String msg =
                        getString(R.string.job_to_do) + ": " +
                                passJob.getDescription() + " " +
                                getString(R.string.vineyard_name) + ": " + passJob.getWinelot() + " " +
                                getString(R.string.deadline) + ": " + passJob.getDeadline();

                Log.d("SEND MAIL: ", address + " " + msg);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.job_to_do));
                i.putExtra(Intent.EXTRA_TEXT, msg);
                try {
                    Toast.makeText(getApplicationContext(), "Sending mail", Toast.LENGTH_SHORT).show();
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
                displayHideFAB();
            }
        });
        floatingActionButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number = "tel:" + passJob.getWorker().getPhone();

                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse(number));
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Log.d("CALLING WORKER: ", number);
                startActivity(phoneIntent);
            }
        });
    }

    private void displayHideFAB(){
        if (floatingActionButtonEdit.getVisibility() == View.VISIBLE) {
            floatingActionButtonMsg.setVisibility(View.GONE);
            floatingActionButtonEdit.setVisibility(View.GONE);
            floatingActionButtonMail.setVisibility(View.GONE);
            floatingActionButtonPhone.setVisibility(View.GONE);
        } else {
            floatingActionButtonMsg.setVisibility(View.VISIBLE);
            floatingActionButtonEdit.setVisibility(View.VISIBLE);
            floatingActionButtonMail.setVisibility(View.VISIBLE);
            floatingActionButtonPhone.setVisibility(View.VISIBLE);
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
