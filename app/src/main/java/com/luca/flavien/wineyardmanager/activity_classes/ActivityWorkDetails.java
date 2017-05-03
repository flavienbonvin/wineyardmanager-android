package com.luca.flavien.wineyardmanager.activity_classes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: activity_classes
 *
 * Description: Display the information of a job
 */

public class ActivityWorkDetails extends AppCompatActivity {

    private FloatingActionButton floatingActionButtonMsg;
    private FloatingActionButton floatingActionButtonEdit;
    private FloatingActionButton floatingActionButtonMail;
    private FloatingActionButton floatingActionButtonPhone;
    private FloatingActionButton floatingActionButtonCalendar;

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

        FloatingActionButton floatingActionButtonMenu = (FloatingActionButton) findViewById(R.id.fab_menu);
        floatingActionButtonMsg = (FloatingActionButton) findViewById(R.id.fab_send_message);
        floatingActionButtonEdit = (FloatingActionButton) findViewById(R.id.fab_edit_work);
        floatingActionButtonMail = (FloatingActionButton) findViewById(R.id.fab_send_mail);
        floatingActionButtonPhone = (FloatingActionButton) findViewById(R.id.fab_call_worker);
        floatingActionButtonCalendar = (FloatingActionButton) findViewById(R.id.fab_calendar);
        floatingActionButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                displayHideFAB();
                setFloatingActionButtonListener();
            }
        });
    }

    /*
     * All the methods of the FloatingActionButton here because there is a lot of FAB in the layout
     *
     * We create a listener for every FAB and make the required operations
     */
    private void setFloatingActionButtonListener() {

        /*
         * Allow to edit the job
         */
        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEdit = new Intent(getApplicationContext(), ActivityWorkAdd.class);
                intentEdit.putExtra("job", passJob);
                startActivity(intentEdit);
                finish();
            }
        });

        /*
         * Allow to send a SMS to the worker
         */
        floatingActionButtonMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = passJob.getWorker().getPhone();

                String msg =
                        getString(R.string.job_to_do) + ": " + passJob.getDescription() + "\n" +
                        getString(R.string.vineyard_name) + ": " + passJob.getWinelot().getName() + "\n" +
                        getString(R.string.wine_variety) + ": " + passJob.getWinelot().getWineVariety().getName() + "\n" +
                        getString(R.string.Deadline) + ": " + passJob.getDeadline();

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

        /*
         * Allow to send a email to the worker
         */
        floatingActionButtonMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = passJob.getWorker().getMail();

                String msg =
                        getString(R.string.job_to_do) + ": " + passJob.getDescription() + "\n" +
                        getString(R.string.vineyard_name) + ": " + passJob.getWinelot().getName() + "\n" +
                        getString(R.string.wine_variety) + ": " + passJob.getWinelot().getWineVariety().getName() + "\n" +
                        getString(R.string.Deadline) + ": " + passJob.getDeadline();

                Log.d("SEND MAIL: ", address + " " + msg);

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
                i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.job_to_do));
                i.putExtra(Intent.EXTRA_TEXT, msg);
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(), R.string.no_email_client, Toast.LENGTH_SHORT).show();
                }
                displayHideFAB();
            }
        });

        /**
         * Allow to call the worker
         */
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
                displayHideFAB();
            }
        });

        /**
         * Allow to add a calendar event
         */
        floatingActionButtonCalendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_EDIT);

                String title = passJob.getDescription();
                String date = passJob.getDeadline();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateEvent = new Date();
                try {
                    dateEvent = simpleDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.d("DATE FORMAT: ", dateEvent.toString());

                String desc =
                        getString(R.string.job_to_do) + ": " + passJob.getDescription() + "\n" +
                        getString(R.string.employee) + ": " + passJob.getWorker().getLastName() + " " + passJob.getWorker().getFirstName() + "\n" +
                        getString(R.string.vineyard_name) + ": " + passJob.getWinelot().getName() + "\n" +
                        getString(R.string.wine_variety) + ": " + passJob.getWinelot().getWineVariety().getName();

                String location = passJob.getWinelot().getName();


                intent.setType("vnd.android.cursor.item/event");
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, dateEvent.getTime());
                intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, dateEvent.getTime());
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                intent.putExtra(CalendarContract.Events.TITLE, title);
                intent.putExtra(CalendarContract.Events.DESCRIPTION, desc);
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location);

                startActivity(intent);
                displayHideFAB();
            }
        });
    }

    //Allow to hide or display the FAB, it only invert the current state
    private void displayHideFAB(){
        if (floatingActionButtonEdit.getVisibility() == View.VISIBLE) {
            floatingActionButtonMsg.setVisibility(View.GONE);
            floatingActionButtonEdit.setVisibility(View.GONE);
            floatingActionButtonMail.setVisibility(View.GONE);
            floatingActionButtonPhone.setVisibility(View.GONE);
            floatingActionButtonCalendar.setVisibility(View.GONE);
        } else {
            floatingActionButtonMsg.setVisibility(View.VISIBLE);
            floatingActionButtonEdit.setVisibility(View.VISIBLE);
            floatingActionButtonMail.setVisibility(View.VISIBLE);
            floatingActionButtonPhone.setVisibility(View.VISIBLE);
            floatingActionButtonCalendar.setVisibility(View.VISIBLE);
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
