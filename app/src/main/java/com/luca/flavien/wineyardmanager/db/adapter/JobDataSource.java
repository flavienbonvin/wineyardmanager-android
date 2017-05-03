package com.luca.flavien.wineyardmanager.db.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.luca.flavien.wineyardmanager.db.Contract;
import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.db.SQLhelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Flavien and Luca on 24.04.2017.
 *
 * Project : WineYardManager
 * Package: adapter
 *
 * Description: All functions for the Job table
 */

public class JobDataSource {

    private final SQLiteDatabase db;
    private final WorkerDataSource workerDataS ;
    private final WineLotDataSource wineLotDataS;

    public JobDataSource(Context context){
        workerDataS = new WorkerDataSource(context);
        wineLotDataS = new WineLotDataSource(context);
        SQLhelper sqliteHelper = SQLhelper.getInstance(context);
        db = sqliteHelper.getWritableDatabase();
    }

    /**
     * Insert a new job
     */
    public long createJob(Job job){
        long id;
        ContentValues values = new ContentValues();
        values.put(Contract.JobEntry.KEY_DESCRIPTION, job.getDescription());
        values.put(Contract.JobEntry.KEY_DEADLINE, job.getDeadline());
        values.put(Contract.JobEntry.KEY_WINELOT_ID, job.getWinelot().getId());
        values.put(Contract.JobEntry.KEY_WORKER_ID, job.getWorker().getId());
        id = this.db.insert(Contract.JobEntry.TABLE_JOB, null, values);
        return id;
    }

    /**
     * Find one Job by Id, , we did it, if needed
     */
    public Job getJobById(long id){
        String sql = "SELECT * FROM " + Contract.JobEntry.TABLE_JOB +
                " WHERE " + Contract.JobEntry.KEY_ID + " = " + id;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Job job = new Job();

        if (cursor != null) {
            job.setId(cursor.getInt(cursor.getColumnIndex(Contract.JobEntry.KEY_ID)));
            job.setDescription(cursor.getString(cursor.getColumnIndex(Contract.JobEntry.KEY_DESCRIPTION)));
            job.setDeadline(cursor.getString(cursor.getColumnIndex(Contract.JobEntry.KEY_DEADLINE)));
            int idWineLot = (cursor.getInt(cursor.getColumnIndex(Contract.JobEntry.KEY_WINELOT_ID)));
            job.setWinelot(wineLotDataS.getWineLotById(idWineLot));
            int idWorker = cursor.getInt(cursor.getColumnIndex(Contract.JobEntry.KEY_WORKER_ID));
            job.setWorker(workerDataS.getWorkerById(idWorker));
        }

        cursor.close();
        return job;
    }

    /**
     * Get all Jobs
     */
    public List<Job> getAllJobs(){
        List<Job> jobs = new ArrayList<>();
        String sql = "SELECT * FROM " + Contract.JobEntry.TABLE_JOB + " ORDER BY " + Contract.JobEntry.KEY_DEADLINE;

        Cursor cursor = this.db.rawQuery(sql, null);

        if(cursor.moveToFirst()){
            do{
                Job job = new Job();
                job.setId(cursor.getInt(cursor.getColumnIndex(Contract.JobEntry.KEY_ID)));
                job.setDescription(cursor.getString(cursor.getColumnIndex(Contract.JobEntry.KEY_DESCRIPTION)));
                job.setDeadline(cursor.getString(cursor.getColumnIndex(Contract.JobEntry.KEY_DEADLINE)));
                int idWineLot = (cursor.getInt(cursor.getColumnIndex(Contract.JobEntry.KEY_WINELOT_ID)));
                job.setWinelot(wineLotDataS.getWineLotById(idWineLot));
                int idWorker = cursor.getInt(cursor.getColumnIndex(Contract.JobEntry.KEY_WORKER_ID));
                job.setWorker(workerDataS.getWorkerById(idWorker));

                jobs.add(job);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return jobs;
    }

    /**
     *  Update a Job
     */
    public int updateJob(Job job){
        ContentValues values = new ContentValues();
        values.put(Contract.JobEntry.KEY_DESCRIPTION, job.getDescription());
        values.put(Contract.JobEntry.KEY_DEADLINE, job.getDeadline());
        values.put(Contract.JobEntry.KEY_WINELOT_ID, job.getWinelot().getId());
        values.put(Contract.JobEntry.KEY_WORKER_ID, job.getWorker().getId());

        return this.db.update(Contract.JobEntry.TABLE_JOB, values, Contract.JobEntry.KEY_ID + " = ?",
                new String[] { String.valueOf(job.getId()) });
    }
    /**
     *  Delete a Job, we did it, if needed
     */
    public void deleteWork(long id){

        String selection = Contract.JobEntry.KEY_ID + " LIKE ? ";
        String[] args = {String.valueOf(id)};


        db.delete(Contract.JobEntry.TABLE_JOB, selection, args);
    }
}
