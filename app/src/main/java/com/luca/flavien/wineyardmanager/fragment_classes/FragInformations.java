package com.luca.flavien.wineyardmanager.fragment_classes;

/**
 * Created by flavien on 11.05.17.
 */


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luca.flavien.wineyardmanager.R;

public class FragInformations extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragement_informations, container, false);

        return view ;
    }
}