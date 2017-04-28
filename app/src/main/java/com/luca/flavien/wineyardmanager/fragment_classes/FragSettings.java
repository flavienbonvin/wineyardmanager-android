package com.luca.flavien.wineyardmanager.fragment_classes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.WorkAdapter;
import com.luca.flavien.wineyardmanager.activity_classes.ActivityWorkDetails;
import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.db.object.Orientation;
import com.luca.flavien.wineyardmanager.db.object.WineLot;

import java.util.List;
import java.util.Locale;

/**
 * Created by Flavien on 24.04.2017.
 */

public class FragSettings extends Fragment {

    private Spinner spinnerLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        spinnerLanguage = (Spinner)view.findViewById(R.id.spinner_langue);
        updateSpinner();

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_confirm_language);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinnerLanguage.getSelectedItem().toString()){
                    case "Deutsch":
                        Toast.makeText(getContext(), R.string.willkommen,Toast.LENGTH_SHORT).show();
                        langueGerman(v);
                        break;
                    case "Français":
                        Toast.makeText(getContext(), R.string.bienvenue,Toast.LENGTH_SHORT).show();
                        langueFrench(v);
                        break;
                    case "English":
                        Toast.makeText(getContext(), R.string.welcome,Toast.LENGTH_SHORT).show();
                        langueEnglish(v);
                        break;
                    case "Italiano":
                        Toast.makeText(getContext(), R.string.welcome,Toast.LENGTH_SHORT).show();
                        langueEnglish(v);
                        break;
                }
                }

            });


        return view;
    }

    private void updateSpinner() {
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<>
                (getActivity(), R.layout.row_simple, MainActivity.languageList);
        spinnerLanguage.setAdapter(adapterLanguage);
    }
    public void langueFrench(View v)
    {
        String languageToLoad  = "fr";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    public void langueGerman(View v)
    {
        String languageToLoad  = "de";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void langueEnglish(View v)
    {
        String languageToLoad  = "en";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
    public void langueItalian(View v)
    {
        String languageToLoad  = "it";
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config,v.getResources().getDisplayMetrics());

        Intent intent = new Intent(getActivity(),MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
