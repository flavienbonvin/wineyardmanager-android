package com.luca.flavien.wineyardmanager.DB.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luca.flavien.wineyardmanager.DB.Contract;
import com.luca.flavien.wineyardmanager.DB.Object.Orientation;
import com.luca.flavien.wineyardmanager.DB.SQLhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class OrientationDataSource {
    private final SQLiteDatabase db;

    public OrientationDataSource(Context context){
        SQLhelper sqliteHelper = SQLhelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * Insert a new Orientation
     */
    public long createOrientation(Orientation Orientation){
        long id;
        ContentValues values = new ContentValues();
        values.put(Contract.OrientationEntry.KEY_NAME, Orientation.getName());
        id = this.db.insert(Contract.OrientationEntry.TABLE_ORIENTATION, null, values);
        return id;
    }

    /**
     * Find one Orientation by Id
     */
    public Orientation getOrientationById(long id){
        String sql = "SELECT * FROM " + Contract.OrientationEntry.TABLE_ORIENTATION +
                " WHERE " + Contract.OrientationEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Orientation orientation = new Orientation();
        orientation.setId(cursor.getInt(cursor.getColumnIndex(Contract.OrientationEntry.KEY_ID)));
        orientation.setName(cursor.getString(cursor.getColumnIndex(Contract.OrientationEntry.KEY_NAME)));

        cursor.close();
        return orientation;
    }

    /**
     * Get all Orientations
     */
    public List<Orientation> getAllOrientations(){
        List<Orientation> orientations = new ArrayList<>();
        String sql = "SELECT * FROM " + Contract.OrientationEntry.TABLE_ORIENTATION + " ORDER BY " + Contract.OrientationEntry.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Orientation orientation = new Orientation();
                orientation.setId(cursor.getInt(cursor.getColumnIndex(Contract.OrientationEntry.KEY_ID)));
                orientation.setName(cursor.getString(cursor.getColumnIndex(Contract.OrientationEntry.KEY_NAME)));

                orientations.add(orientation);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return orientations;
    }

    /**
     *  Update a Orientation
     */
    public int updateOrientation(Orientation Orientation){
        ContentValues values = new ContentValues();
        values.put(Contract.OrientationEntry.KEY_NAME, Orientation.getName());

        return this.db.update(Contract.OrientationEntry.TABLE_ORIENTATION, values, Contract.OrientationEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(Orientation.getId()) });
    }

    /*
     * Delete a Orientation - this will also delete all his Orientation ?????
     * for the Orientation
     */

   /* public void deleteOrientation(long id){
        RecordDataSource pra = new RecordDataSource(context);
        //get all records of the user
        List<Record> records = pra.getAllRecordsByPerson(id);

        for(Record record : records){
            pra.deleteRecord(record.getId());
        }

        //delete the person
        this.db.delete(PersonEntry.TABLE_PERSON, PersonEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(id) });

    }*/
}
