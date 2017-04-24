package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragLocationList extends Fragment implements AdapterView.OnItemClickListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        String[] action ={"A",
                "B",
                "C",
                "D"};

        ListView listView = (ListView)view.findViewById(R.id.list_of_location);

        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.row_location,
                R.id.location_name,
                action
        );

        listView.setAdapter(listViewAdapter);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
