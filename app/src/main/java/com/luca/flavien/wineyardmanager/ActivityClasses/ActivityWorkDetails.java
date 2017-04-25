package com.luca.flavien.wineyardmanager.ActivityClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.luca.flavien.wineyardmanager.DB.Object.Job;
import com.luca.flavien.wineyardmanager.R;

import org.w3c.dom.Text;

import static com.luca.flavien.wineyardmanager.R.id.textView;

/**
 * Created by Flavien on 24.04.2017.
 */

public class ActivityWorkDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_detail);

        Intent intent = getIntent();
        Job passJob = (Job) intent.getSerializableExtra("Job");

        TextView textViewDescription = (TextView) findViewById(R.id.description);
        TextView textViewDeadLine = (TextView) findViewById(R.id.deadLine);
        TextView textViewWineLotName = (TextView) findViewById(R.id.wineLotName);
        TextView textViewWorkerName = (TextView) findViewById(R.id.workerName);

        textViewDescription.setText(passJob.getDescription());
        textViewDeadLine.setText(passJob.getDeadline());
        textViewWineLotName.setText(passJob.getWinelot().getName());
        textViewWorkerName.setText(passJob.getWorker().getLastName()+" "+passJob.getWorker().getFirstName());
    }
}
