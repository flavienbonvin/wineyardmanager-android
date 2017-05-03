package com.luca.flavien.wineyardmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: db
 *
 * Description: We use this class for long-running operations of creating or updating the DB
 *              only when when we need it (not during the app start up)
 */

public class SQLhelper extends SQLiteOpenHelper {

    private final SQLiteDatabase db;

    //Info about database
    private static final String DATABASE_NAME = "survey.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLhelper instance;


    //use a singleton
    //we want always just one instance of the database
    private SQLhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();

        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    public static SQLhelper getInstance(Context context){
        if(instance == null){
            instance = new SQLhelper(context.getApplicationContext());

            //Enable foreign key support
            instance.db.execSQL("PRAGMA foreign_keys = ON;");
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Contract.WorkerEntry.CREATE_TABLE_WORKER);
        db.execSQL(Contract.JobEntry.CREATE_TABLE_JOB);
        db.execSQL(Contract.WineVarietyEntry.CREATE_TABLE_WINEVARIETY);
        db.execSQL(Contract.WineLotEntry.CREATE_TABLE_WINELOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + Contract.WorkerEntry.TABLE_WORKER);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.JobEntry.TABLE_JOB);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.WineLotEntry.TABLE_WINELOT);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.WineVarietyEntry.TABLE_WINEVARIETY);

        //create new tables
        onCreate(db);
    }
}
