package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragEmployee extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee,container, false);

        String[] datasource ={"Flavien Bonvin",
                "Luca Centofanti",
                "Pedro Gil Ferreira",
                "Maxime Bétrisey"};

        ListView listView = (ListView)view.findViewById(R.id.list_of_employee);

        ArrayAdapter<String> listviewadapter = new ArrayAdapter<>(
                getActivity(),
                R.layout.row_employee,
                R.id.Itemname,
                datasource
        );
        listView.setAdapter(listviewadapter);
        return view;
    }
}
