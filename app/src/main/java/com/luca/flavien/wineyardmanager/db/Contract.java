package com.luca.flavien.wineyardmanager.db;

import android.provider.BaseColumns;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: db
 *
 * Description: Global definition (static), we can access from the all project
                Inner class for each table that enumerate columns
 */

public class Contract {

    //Represents the rows of a table
    public static abstract class WorkerEntry implements BaseColumns {
        //Table name
        public static final String TABLE_WORKER = "worker";

        //Person Column names
        public static final String KEY_ID = "id";
        public static final String KEY_LASTNAME = "lastName";
        public static final String KEY_FIRSTNAME = "firstName";
        public static final String KEY_PHONE = "phone";
        public static final String KEY_MAIL = "mail";

        //Table person create statement
        public static final String CREATE_TABLE_WORKER =
                "CREATE TABLE " + TABLE_WORKER
                + "("
                + WorkerEntry.KEY_ID            + " INTEGER PRIMARY KEY,"
                + WorkerEntry.KEY_LASTNAME      + " TEXT, "
                + WorkerEntry.KEY_FIRSTNAME     + " TEXT, "
                + WorkerEntry.KEY_PHONE         + " TEXT, "
                + WorkerEntry.KEY_MAIL          + " TEXT "
                + ");";


    }

    public static abstract class JobEntry implements BaseColumns{
        //Table name
        public static final String TABLE_JOB = "job";

        //Record Column names
        public static final String KEY_ID = "id";
        public static final String KEY_DESCRIPTION = "description";
        public static final String KEY_DEADLINE= "deadline";
        public static final String KEY_WINELOT_ID = "winelot_id";
        public static final String KEY_WORKER_ID = "worker_id";


        //Table record create statement
        public static final String CREATE_TABLE_JOB =
                "CREATE TABLE " + TABLE_JOB
                + "("
                + JobEntry.KEY_ID               + " INTEGER PRIMARY KEY,"
                + JobEntry.KEY_DESCRIPTION      + " TEXT, "
                + JobEntry.KEY_DEADLINE         + " TEXT, "
                + JobEntry.KEY_WINELOT_ID       + " INTEGER, "
                + JobEntry.KEY_WORKER_ID        + " INTEGER, "
                + "FOREIGN KEY (" + KEY_WINELOT_ID +") REFERENCES " + WineLotEntry.TABLE_WINELOT+ " (" + KEY_ID + ") ON DELETE CASCADE , "
                + "FOREIGN KEY (" + KEY_WORKER_ID +") REFERENCES " + WorkerEntry.TABLE_WORKER+ " (" + KEY_ID + ")  ON DELETE CASCADE "
                + ");";
    }

    public static abstract class WineLotEntry implements BaseColumns{
        //Table name
        public static final String TABLE_WINELOT = "wine_lot";

        //Record Column names
        public static final String KEY_ID = "id";
        public static final String KEY_NAME= "name";
        public static final String KEY_SURFACE = "surface" ;
        public static final String KEY_NUMWINESTOCK= "numWineStock" ;
        public static final String KEY_PICTURE= "picture";
        public static final String KEY_WINEVARIETY_ID = "winevariety_id";
        public static final String KEY_ORIENTATION_ID = "orientation_id";
        public static final String KEY_LONGITUDE = "longitude";
        public static final String KEY_LATITUDE = "latitude";



        //Table record create statement
        public static final String CREATE_TABLE_WINELOT=
                "CREATE TABLE " + TABLE_WINELOT
                + "("
                + WineLotEntry.KEY_ID               + " INTEGER PRIMARY KEY, "
                + WineLotEntry.KEY_NAME             + " DATETIME, "
                + WineLotEntry.KEY_SURFACE          + " TEXT, "
                + WineLotEntry.KEY_NUMWINESTOCK     + " TEXT, "
                + WineLotEntry.KEY_PICTURE          + " TEXT, "
                + WineLotEntry.KEY_WINEVARIETY_ID   + " INTEGER, "
                + WineLotEntry.KEY_ORIENTATION_ID   + " INTEGER, "
                + WineLotEntry.KEY_LONGITUDE        + " DOUBLE, "
                + WineLotEntry.KEY_LATITUDE         + " DOUBLE, "
                + "FOREIGN KEY (" + KEY_WINEVARIETY_ID + ") REFERENCES " + WineVarietyEntry.TABLE_WINEVARIETY + " (" + KEY_ID + ") ON DELETE CASCADE "
                + ");";
    }

    public static abstract class WineVarietyEntry implements BaseColumns{
        //Table name
        public static final String TABLE_WINEVARIETY= "wine_variety";

        //Record Column names
        public static final String KEY_ID = "id";
        public static final String KEY_NAME= "name";


        //Table record create statement
        public static final String CREATE_TABLE_WINEVARIETY =
                "CREATE TABLE " + TABLE_WINEVARIETY
                + "("
                + WineVarietyEntry.KEY_ID       + " INTEGER PRIMARY KEY,"
                + WineVarietyEntry.KEY_NAME     + " TEXT "
                + ");";
    }
}