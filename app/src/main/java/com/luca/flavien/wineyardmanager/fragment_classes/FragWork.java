package com.luca.flavien.wineyardmanager.fragment_classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luca.flavien.wineyardmanager.WorkAdapter;
import com.luca.flavien.wineyardmanager.activity_classes.ActivityWorkAdd;
import com.luca.flavien.wineyardmanager.activity_classes.ActivityWorkDetails;
import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragWork extends Fragment{

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work,container, false);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add_work);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ActivityWorkAdd.class);
                getActivity().startActivity(intent);
            }
        });



        listView = (ListView)view.findViewById(R.id.listofwork);

        updateDisplay();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(),ActivityWorkDetails.class);
            intent.putExtra("job", (Job)parent.getItemAtPosition(position));
            startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateDisplay();
    }

    private void updateDisplay(){
        List<Job> listJob = MainActivity.jobDataSource.getAllJobs();

        ArrayAdapter<Job> workAdapter = new WorkAdapter(getActivity(), listJob);
        listView.setAdapter(workAdapter);
    }
}
