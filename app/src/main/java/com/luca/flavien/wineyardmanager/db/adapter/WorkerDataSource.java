package com.luca.flavien.wineyardmanager.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.db.Contract;
import com.luca.flavien.wineyardmanager.db.object.Worker;
import com.luca.flavien.wineyardmanager.db.SQLhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien on 24.04.2017.
 */

public class WorkerDataSource {

    private SQLiteDatabase db;

    private  Context context;

    public WorkerDataSource(Context context){
        this.context = context;

        SQLhelper sqliteHelper = SQLhelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * Insert a new worker
     */
    public long createWorker(Worker worker){
        long id;
        ContentValues values = new ContentValues();
        values.put(Contract.WorkerEntry.KEY_FIRSTNAME, worker.getFirstName());
        values.put(Contract.WorkerEntry.KEY_LASTNAME, worker.getLastName());
        values.put(Contract.WorkerEntry.KEY_PHONE, worker.getPhone());
        values.put(Contract.WorkerEntry.KEY_MAIL, worker.getMail());
        id = this.db.insert(Contract.WorkerEntry.TABLE_WORKER, null, values);
        return id;
    }

    /**
     * Find one Worker by Id
     */
    public Worker getWorkerById(long id){
        String sql = "SELECT * FROM " + Contract.WorkerEntry.TABLE_WORKER +
                " WHERE " + Contract.WorkerEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Worker worker = new Worker();
        worker.setId(cursor.getInt(cursor.getColumnIndex(Contract.WorkerEntry.KEY_ID)));
        worker.setFirstName(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_FIRSTNAME)));
        worker.setLastName(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_LASTNAME)));
        worker.setPhone(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_PHONE)));
        worker.setMail(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_MAIL)));

        cursor.close();
        return worker;
    }

    /**
     * Get all Workers
     */
    public List<Worker> getAllWorkers(){
        List<Worker> workers = new ArrayList<>();
        String sql = "SELECT * FROM " + Contract.WorkerEntry.TABLE_WORKER + " ORDER BY " + Contract.WorkerEntry.KEY_LASTNAME;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Worker worker = new Worker();
                worker.setId(cursor.getInt(cursor.getColumnIndex(Contract.WorkerEntry.KEY_ID)));
                worker.setFirstName(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_FIRSTNAME)));
                worker.setLastName(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_LASTNAME)));
                worker.setPhone(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_PHONE)));
                worker.setMail(cursor.getString(cursor.getColumnIndex(Contract.WorkerEntry.KEY_MAIL)));

                workers.add(worker);
            } while(cursor.moveToNext());
        }

        cursor.close();
        return workers;
    }

    /**
     *  Update a Worker
     */
    public int updateWorker(Worker worker){
        int updated;

        SQLhelper sqliteHelper = SQLhelper.getInstance(context);
        db = sqliteHelper.getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(Contract.WorkerEntry.KEY_FIRSTNAME, worker.getFirstName());
        values.put(Contract.WorkerEntry.KEY_LASTNAME, worker.getLastName());
        values.put(Contract.WorkerEntry.KEY_PHONE, worker.getPhone());
        values.put(Contract.WorkerEntry.KEY_MAIL, worker.getMail());


        String[] arg = {String.valueOf(worker.getId())};
        String idRow = Contract.WorkerEntry.KEY_ID + " LIKE ?";

        updated = db.update(
                Contract.WorkerEntry.TABLE_WORKER,
                values,
                idRow,
                arg);

        return updated;
    }

    /* public void deleteWorkerContract.WorkerEntry.KEY_ID + " (long id){
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
