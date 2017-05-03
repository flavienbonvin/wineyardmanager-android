package com.luca.flavien.wineyardmanager.fragment_classes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.db.object.Orientation;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: fragment_classes
 *
 * Description: Show the list of Orientaions
 */

public class FragOrientation extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_orientation, container, false);


        ArrayAdapter<Orientation> adapter = new ArrayAdapter<>
                (getActivity(), R.layout.row_simple, MainActivity.orientationList);

        ListView listView = (ListView)view.findViewById(R.id.list_of_orientation);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), R.string.no_details, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
