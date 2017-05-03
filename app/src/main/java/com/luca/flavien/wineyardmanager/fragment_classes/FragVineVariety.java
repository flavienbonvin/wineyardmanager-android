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

import com.luca.flavien.wineyardmanager.activity_classes.ActivityVarietyAdd;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.List;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: fragment_classes
 *
 * Description: Show the list of VineVariety
 */

public class FragVineVariety extends Fragment{

    private ListView listView;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
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
                Intent intent = new Intent(getActivity(), ActivityVarietyAdd.class);
                intent.putExtra("variety", (WineVariety)parent.getItemAtPosition(position));
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
        List<WineVariety> varietyList = MainActivity.wineVarietyDataSource.getAllWineVarieties();
        ArrayAdapter<WineVariety> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_simple, varietyList);
        listView.setAdapter(adapter);
    }
}
