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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.R;
import com.luca.flavien.wineyardmanager.cloud.CloudManager;

import java.util.Locale;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: fragment_classes
 *
 * Description: Show the languages options
 */

public class FragSettings extends Fragment {

    private Spinner spinnerLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        spinnerLanguage = (Spinner)view.findViewById(R.id.spinner_language);
        updateSpinner();
        spinnerLanguage.setSelection(MainActivity.languagePosition);

        FloatingActionButton floatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab_confirm_language);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinnerLanguage.getSelectedItem().toString()){
                    case "Deutsch":
                        MainActivity.languagePosition = 0;
                        Toast.makeText(getContext(), R.string.willkommen,Toast.LENGTH_SHORT).show();
                        langueGerman(v);
                        break;
                    case "Français":
                        MainActivity.languagePosition = 1;
                        Toast.makeText(getContext(), R.string.bienvenue,Toast.LENGTH_SHORT).show();
                        langueFrench(v);
                        break;
                    case "English":
                        MainActivity.languagePosition = 2;
                        Toast.makeText(getContext(), R.string.welcome,Toast.LENGTH_SHORT).show();
                        langueEnglish(v);
                        break;
                    case "Italiano":
                        MainActivity.languagePosition = 3;
                        Toast.makeText(getContext(), R.string.benvenuto,Toast.LENGTH_SHORT).show();
                        langueItalian(v);
                        break;
                }
                }

            });


        Button buttonSync = (Button)view.findViewById(R.id.btSync);
        buttonSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloudManager.SendToCloud();
            }
        });

        return view;
    }

    private void updateSpinner() {
        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<>
                (getActivity(), R.layout.row_simple, MainActivity.languageList);
        spinnerLanguage.setAdapter(adapterLanguage);
    }
    //Set the language to French
    private void langueFrench(View v)
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
    //Set the language to German
    private void langueGerman(View v)
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
    //Set the language to English
    private void langueEnglish(View v)
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
    //Set the language to Italian
    private void langueItalian(View v)
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
