package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityLocationAdd;
import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityLocationDetail;
import com.luca.flavien.wineyardmanager.DB.Object.Orientation;
import com.luca.flavien.wineyardmanager.DB.Object.WineLot;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;

/**
 * Created by flavien on 26/04/17.
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
                Toast.makeText(getActivity(), "There is no details", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
