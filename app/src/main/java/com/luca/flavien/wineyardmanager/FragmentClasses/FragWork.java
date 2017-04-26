package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityEmployeeAdd;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityEmployeeDetail;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityWorkAdd;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityWorkDetails;
import com.luca.flavien.wineyardmanager.DB.Object.Job;
import com.luca.flavien.wineyardmanager.DB.Object.Orientation;
import com.luca.flavien.wineyardmanager.DB.Object.WineLot;
import com.luca.flavien.wineyardmanager.DB.Object.WineVariety;
import com.luca.flavien.wineyardmanager.DB.Object.Worker;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.WorkAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragWork extends Fragment{
    private List<Job> jobs;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work,container, false);

        Worker worker = new Worker();
        worker.setId(1);
        worker.setFirstName("Luca");
        worker.setLastName("Centofanti");
        worker.setMail("lucacentofan@gmail.com");
        worker.setPhone("0788129662");

        WineVariety wineVariety1 = new WineVariety();
        wineVariety1.setId(1);
        wineVariety1.setName("GAMAY");

        Orientation orientation = new Orientation();
        orientation.setName("Ouest");
        orientation.setId(1);

        WineLot wineLot = new WineLot();
        wineLot.setId(1);
        wineLot.setName("Sion&Region");
        wineLot.setSurface(140);
        wineLot.setNumberWineStock(100);
        wineLot.setOrientation(orientation);
        wineLot.setPicture("hello");
        wineLot.setWineVariety(wineVariety1);

        Job job1 = new Job();
        job1.setId(1);
        job1.setWinelot(wineLot);
        job1.setWorker(worker);
        job1.setDescription("Taille");
        job1.setDeadline("28.12.2017");

        Job job2 = new Job();
        job2.setId(2);
        job2.setWinelot(wineLot);
        job2.setWorker(worker);
        job2.setDescription("Coupe");
        job2.setDeadline("28.12.2017");

        Job job3 = new Job();
        job3.setId(3);
        job3.setWinelot(wineLot);
        job3.setWorker(worker);
        job3.setDescription("Désherbage");
        job3.setDeadline("28.12.2017");

        jobs = new ArrayList<>();
        jobs.add(job1);
        jobs.add(job2);
        jobs.add(job3);

        ListView listView = (ListView)view.findViewById(R.id.listofwork);

        WorkAdapter listViewAdapter = new WorkAdapter(
                getActivity(),
                jobs
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getActivity(),ActivityWorkDetails.class);
                intent.putExtra("Job",jobs.get(position));
                startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add_work);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ActivityWorkAdd.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
