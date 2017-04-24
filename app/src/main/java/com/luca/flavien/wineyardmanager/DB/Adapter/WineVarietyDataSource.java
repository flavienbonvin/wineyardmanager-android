package com.luca.flavien.wineyardmanager.DB.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luca.flavien.wineyardmanager.DB.Contract;
import com.luca.flavien.wineyardmanager.DB.Object.WineVariety;
import com.luca.flavien.wineyardmanager.DB.SQLhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WineVarietyDataSource {
    private final SQLiteDatabase db;

    public WineVarietyDataSource(Context context){
        SQLhelper sqliteHelper = SQLhelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * Insert a new WineVariety
     */
    public long createWineVariety(WineVariety wineVariety){
        long id;
        ContentValues values = new ContentValues();
        values.put(Contract.WineVarietyEntry.KEY_NAME, wineVariety.getName());
        id = this.db.insert(Contract.WineVarietyEntry.TABLE_WINEVARIETY, null, values);
        return id;
    }

    /**
     * Find one WineVariety by Id
     */
    public WineVariety getWineVarietyById(long id){
        String sql = "SELECT * FROM " + Contract.WineVarietyEntry.TABLE_WINEVARIETY +
                " WHERE " + Contract.WineVarietyEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        WineVariety wineVariety = new WineVariety();
        wineVariety.setId(cursor.getInt(cursor.getColumnIndex(Contract.WineVarietyEntry.KEY_ID)));
        wineVariety.setName(cursor.getString(cursor.getColumnIndex(Contract.WineVarietyEntry.KEY_NAME)));

        cursor.close();
        return wineVariety;
    }

    /**
     * Get all WineVarieties
     */
    public List<WineVariety> getAllWineVarieties(){
        List<WineVariety> wineVarieties = new ArrayList<>();
        String sql = "SELECT * FROM " + Contract.WineVarietyEntry.TABLE_WINEVARIETY + " ORDER BY " + Contract.WineVarietyEntry.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                WineVariety wineVariety = new WineVariety();
                wineVariety.setId(cursor.getInt(cursor.getColumnIndex(Contract.WineVarietyEntry.KEY_ID)));
                wineVariety.setName(cursor.getString(cursor.getColumnIndex(Contract.WineVarietyEntry.KEY_NAME)));

                wineVarieties.add(wineVariety);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return wineVarieties;
    }

    /**
     *  Update a WineVariety
     */
    public int updateWineVariety(WineVariety wineVariety){
        ContentValues values = new ContentValues();
        values.put(Contract.WineVarietyEntry.KEY_NAME, wineVariety.getName());

        return this.db.update(Contract.WineVarietyEntry.TABLE_WINEVARIETY, values, Contract.WineVarietyEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(wineVariety.getId()) });
    }

    /**
     * Delete a WineVariety - this will also delete all his WineVariety ?????
     * for the WineVariety
     */

   /* public void deleteWineVariety(long id){
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
