package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityEmployeeAdd;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityEmployeeDetail;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityWorkDetails;
import com.luca.flavien.wineyardmanager.DB.Object.Job;
import com.luca.flavien.wineyardmanager.DB.Object.Worker;
import com.luca.flavien.wineyardmanager.EmployeeAdapter;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.WorkAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;
import static android.support.v7.widget.AppCompatDrawableManager.get;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragEmployee extends Fragment {
    private  List<Worker> workers;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee,container, false);
        ListView listView = (ListView)view.findViewById(R.id.list_of_employee);


        Worker worker1 = new Worker();
        worker1.setId(1);
        worker1.setFirstName("Luca");
        worker1.setLastName("Centofanti");
        worker1.setMail("lucacentofan@gmail.com");
        worker1.setPhone("0788129662");

        Worker worker2 = new Worker();
        worker2.setId(2);
        worker2.setFirstName("Flavien");
        worker2.setLastName("Bonvin");
        worker2.setMail("flavienbonvin@gmail.com");
        worker2.setPhone("0788129222");

        Worker worker3 = new Worker();
        worker3.setId(3);
        worker3.setFirstName("Maxime");
        worker3.setLastName("Carneiro");
        worker3.setMail("maximecarneiro@gmail.com");
        worker3.setPhone("0788124455");


        workers = new ArrayList<>();
        workers.add(worker1);
        workers.add(worker2);
        workers.add(worker3);

        EmployeeAdapter listViewAdapter = new EmployeeAdapter(
                getActivity(),
                workers
        );

        listView.setAdapter(listViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ActivityEmployeeDetail.class);
                intent.putExtra("Worker",workers.get(position));
                startActivity(intent);

            }
        });

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add_employee);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ActivityEmployeeAdd.class);
                getActivity().startActivity(intent);
            }
        });



        return view;
    }



}
