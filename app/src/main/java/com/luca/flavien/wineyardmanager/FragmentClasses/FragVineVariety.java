package com.luca.flavien.wineyardmanager.FragmentClasses;

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
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityLocationAdd;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityVarietyAdd;
import com.luca.flavien.wineyardmanager.DB.Object.Orientation;
import com.luca.flavien.wineyardmanager.DB.Object.WineVariety;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavien on 26/04/17.
 */

public class FragVineVariety extends Fragment{

    private List<WineVariety> varietyList;
    private ArrayAdapter<WineVariety> adapter;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vine_variety, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_variety_list);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityVarietyAdd.class);
                startActivity(intent);
            }
        });

        listView = (ListView)view.findViewById(R.id.list_of_variety);

        updateDisplay();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "There is no details", Toast.LENGTH_SHORT).show();
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
        varietyList = MainActivity.wineVarietyDataSource.getAllWineVarieties();
        adapter = new ArrayAdapter<>(getActivity(), R.layout.row_simple, varietyList);
        listView.setAdapter(adapter);
    }
}
