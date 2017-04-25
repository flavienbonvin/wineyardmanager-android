package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.luca.flavien.wineyardmanager.ActivityClasses.ActivityLocationDetail;
import com.luca.flavien.wineyardmanager.DB.Object.Orientation;
import com.luca.flavien.wineyardmanager.DB.Object.WineLot;
import com.luca.flavien.wineyardmanager.DB.Object.WineVariety;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.WineLotAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragLocationList extends Fragment{

    private ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);

        WineVariety wineVariety1 = new WineVariety();
        wineVariety1.setId(1);
        wineVariety1.setName("GAMAY");

        Orientation orientation = new Orientation();
        orientation.setName("Ouest");
        orientation.setId(1);

        WineLot wineLot1 = new WineLot();
        wineLot1.setName("AAAAAA");
        wineLot1.setNumberWineStock(10);
        wineLot1.setOrientation(orientation);
        wineLot1.setSurface(200);
        wineLot1.setWineVariety(wineVariety1);

        List<WineLot> listWineLot = new ArrayList<>();
        listWineLot.add(wineLot1);

        WineLotAdapter wineLotAdapter = new WineLotAdapter(getActivity(), listWineLot);


        listView = (ListView)view.findViewById(R.id.list_of_location);
        listView.setAdapter(wineLotAdapter);

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
}
