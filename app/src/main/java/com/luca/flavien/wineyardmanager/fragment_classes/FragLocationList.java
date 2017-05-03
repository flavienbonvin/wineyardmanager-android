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

import com.luca.flavien.wineyardmanager.activity_classes.ActivityLocationAdd;
import com.luca.flavien.wineyardmanager.activity_classes.ActivityLocationDetail;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

import java.util.List;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: fragment_classes
 *
 * Description: Show the list of Locations
 */

public class FragLocationList extends Fragment{

    private ListView listView;

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_location_list);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityLocationAdd.class);
                startActivity(intent);
            }
        });

        listView = (ListView)view.findViewById(R.id.list_of_location);

        updateDisplay();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), ActivityLocationDetail.class);
                intent.putExtra("winelot", (WineLot)parent.getItemAtPosition(position));
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
        List<WineLot> listWineLot = MainActivity.wineLotDataSource.getAllWineLots();
        ArrayAdapter<WineLot> adapter = new ArrayAdapter<>(getActivity(), R.layout.row_simple, listWineLot);
        listView.setAdapter(adapter);
    }
}
