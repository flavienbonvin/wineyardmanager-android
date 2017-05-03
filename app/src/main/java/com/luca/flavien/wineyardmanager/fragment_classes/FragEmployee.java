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

import com.luca.flavien.wineyardmanager.activity_classes.ActivityEmployeeAdd;
import com.luca.flavien.wineyardmanager.activity_classes.ActivityEmployeeDetail;
import com.luca.flavien.wineyardmanager.db.object.Worker;
import com.luca.flavien.wineyardmanager.EmployeeAdapter;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.List;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: fragment_classes
 *
 * Description: Show the list of Employees
 */

public class FragEmployee extends Fragment {

    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee,container, false);

        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab_add_employee);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ActivityEmployeeAdd.class);
                startActivity(intent);
            }
        });

        listView = (ListView)view.findViewById(R.id.list_of_employee);

        updateDisplay();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),ActivityEmployeeDetail.class);
                intent.putExtra("Worker", (Worker)parent.getItemAtPosition(position));
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
        List<Worker> workersList = MainActivity.workerDataSource.getAllWorkers();
        ArrayAdapter<Worker> employeeAdapter = new EmployeeAdapter(getActivity(), workersList);
        listView.setAdapter(employeeAdapter);
    }
}
