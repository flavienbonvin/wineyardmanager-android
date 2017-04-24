package com.luca.flavien.wineyardmanager.FragmentClasses;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.luca.flavien.wineyardmanager.R;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragLocationDetail extends Fragment{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
        return inflater.inflate(R.layout.fragment_location_detail, container, false);
    }
}
