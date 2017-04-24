package com.luca.flavien.wineyardmanager.DB.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luca.flavien.wineyardmanager.DB.Contract;
import com.luca.flavien.wineyardmanager.DB.Object.WineLot;
import com.luca.flavien.wineyardmanager.DB.SQLhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WineLotDataSource {
    private final SQLiteDatabase db;

    public WineLotDataSource(Context context){
        SQLhelper sqliteHelper = SQLhelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * Insert a new WineLot
     */
    public long createWineLot(WineLot wineLot){
        long id;
        ContentValues values = new ContentValues();
        values.put(Contract.WineLotEntry.KEY_NAME, wineLot.getName());
        values.put(Contract.WineLotEntry.KEY_PICTURE, wineLot.getPicture());
        values.put(Contract.WineLotEntry.KEY_NUMWINESTOCK, wineLot.getNumberWineStock());
        values.put(Contract.WineLotEntry.KEY_SURFACE, wineLot.getSurface());
        values.put(Contract.WineLotEntry.KEY_WINEVARIETY_ID, wineLot.getWineVarietyId());
        values.put(Contract.WineLotEntry.KEY_ORIENTATION_ID, wineLot.getOrientationId());
        id = this.db.insert(Contract.WineLotEntry.TABLE_WINELOT, null, values);
        return id;
    }

    /**
     * Find one WineLot by Id
     */
    public WineLot getWineLotById(long id){
        String sql = "SELECT * FROM " + Contract.WineLotEntry.TABLE_WINELOT +
                " WHERE " + Contract.WineLotEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }
        WineLot wineLot = new WineLot();
        wineLot.setId(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_ID)));
        wineLot.setName(cursor.getString(cursor.getColumnIndex(Contract.WineLotEntry.KEY_NAME)));
        wineLot.setPicture(cursor.getString(cursor.getColumnIndex(Contract.WineLotEntry.KEY_PICTURE)));
        wineLot.setNumberWineStock(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_NUMWINESTOCK)));
        wineLot.setSurface(cursor.getFloat(cursor.getColumnIndex(Contract.WineLotEntry.KEY_SURFACE)));
        wineLot.setWineVarietyId(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_WINEVARIETY_ID)));
        wineLot.setOrientationId(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_ORIENTATION_ID)));

        cursor.close();
        return wineLot;
    }

    /**
     * Get all WineLots
     */
    public List<WineLot> getAllWineLots(){
        List<WineLot> WineLots = new ArrayList<>();
        String sql = "SELECT * FROM " + Contract.WineLotEntry.TABLE_WINELOT + " ORDER BY " + Contract.WineLotEntry.KEY_NAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                WineLot wineLot = new WineLot();
                wineLot.setId(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_ID)));
                wineLot.setName(cursor.getString(cursor.getColumnIndex(Contract.WineLotEntry.KEY_NAME)));
                wineLot.setPicture(cursor.getString(cursor.getColumnIndex(Contract.WineLotEntry.KEY_PICTURE)));
                wineLot.setNumberWineStock(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_NUMWINESTOCK)));
                wineLot.setSurface(cursor.getFloat(cursor.getColumnIndex(Contract.WineLotEntry.KEY_SURFACE)));
                wineLot.setWineVarietyId(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_WINEVARIETY_ID)));
                wineLot.setOrientationId(cursor.getInt(cursor.getColumnIndex(Contract.WineLotEntry.KEY_ORIENTATION_ID)));

                WineLots.add(wineLot);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return WineLots;
    }

    /**
     *  Update a WineLot
     */
    public int updateWineLot(WineLot wineLot){
        ContentValues values = new ContentValues();
        values.put(Contract.WineLotEntry.KEY_NAME, wineLot.getName());
        values.put(Contract.WineLotEntry.KEY_PICTURE, wineLot.getPicture());
        values.put(Contract.WineLotEntry.KEY_NUMWINESTOCK, wineLot.getNumberWineStock());
        values.put(Contract.WineLotEntry.KEY_SURFACE, wineLot.getSurface());
        values.put(Contract.WineLotEntry.KEY_WINEVARIETY_ID, wineLot.getWineVarietyId());
        values.put(Contract.WineLotEntry.KEY_ORIENTATION_ID, wineLot.getOrientationId());

        return this.db.update(Contract.WineLotEntry.TABLE_WINELOT, values, Contract.WineLotEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(wineLot.getId()) });
    }

    /**
     * Delete a WineLot - this will also delete all his WineLot ?????
     * for the WineLot
     */

   /* public void deleteWineLot(long id){
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
