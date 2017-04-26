package com.luca.flavien.wineyardmanager.db;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Flavien on 24.04.2017.
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
        db.execSQL(Contract.OrientationEntry.CREATE_TABLE_ORIENTATION);
        db.execSQL(Contract.WineLotEntry.CREATE_TABLE_WINELOT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop old tables
        db.execSQL("DROP TABLE IF EXISTS " + Contract.WorkerEntry.TABLE_WORKER);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.JobEntry.TABLE_JOB);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.WineLotEntry.TABLE_WINELOT);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.WineVarietyEntry.TABLE_WINEVARIETY);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.OrientationEntry.TABLE_ORIENTATION);

        //create new tables
        onCreate(db);
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(Query, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }



//	/**
//	 * get datetime as a string
//	 * */
//	private String getDateTime() {
//		SimpleDateFormat dateFormat = new SimpleDateFormat(
//				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//		Date date = new Date();
//		return dateFormat.format(date);
//	}
}
