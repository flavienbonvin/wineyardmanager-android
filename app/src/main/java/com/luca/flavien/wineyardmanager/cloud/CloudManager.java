package com.luca.flavien.wineyardmanager.cloud;

import android.app.ProgressDialog;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.db.Contract;
import com.luca.flavien.wineyardmanager.db.object.Job;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.db.object.Worker;

import java.util.List;

/**
 * Created by flavien on 11.05.17.
 */

public class CloudManager {

    private static ProgressDialog progressDialog;

    public static void SendToCloud(){

        Log.e("STARTING TO SEND", "Sending data to the cloud");
        sendWineVariety();
        sendWorker();
        sendWineLot();
        sendJob();
        Log.d("SEND", "All data synced to the cloud");
    }

    public static void sendWineVariety() {

        Log.i("CLOUD MANAGER", "Sending wine variety to the cloud");

        List<WineVariety> wineVarietyList = MainActivity.wineVarietyDataSource.getAllWineVarieties();

        for (WineVariety wineVariety : wineVarietyList) {
            com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety wineVarietyCloud
                    = new com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety();

            wineVarietyCloud.setId((long)wineVariety.getId());
            wineVarietyCloud.setName(wineVariety.getName());

            new WineVarietyAsyncTask(wineVarietyCloud).execute();
        }
    }

    public static void sendWorker(){

        Log.i("CLOUD MANAGER", "Sending worker to the cloud");

        List<Worker> workerList = MainActivity.workerDataSource.getAllWorkers();

        for (Worker worker : workerList) {
            com.luca.flavien.wineyardmanager.entities.workerApi.model.Worker workerCloud
                    = new com.luca.flavien.wineyardmanager.entities.workerApi.model.Worker();

            workerCloud.setId((long)worker.getId());
            workerCloud.setFirstName(worker.getFirstName());
            workerCloud.setLastName(worker.getLastName());
            workerCloud.setPhone(worker.getPhone());
            workerCloud.setMail(worker.getMail());

            new WorkerAsyncTask(workerCloud).execute();
        }
    }

    public static void sendWineLot(){

        Log.i("CLOUD MANAGER", "Sending wine lot to the cloud");

        List<WineLot> wineLotList = MainActivity.wineLotDataSource.getAllWineLots();

        for (WineLot wineLot : wineLotList){

            com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineLot wineLotCloud
                    = new com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineLot();
            com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineVariety wineVarietyCloud
                    = new com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineVariety();

            wineVarietyCloud.setId((long)wineLot.getWineVariety().getId());
            wineVarietyCloud.setName(wineLot.getWineVariety().getName());

            wineLotCloud.setId((long)wineLot.getId());
            wineLotCloud.setName(wineLot.getName());
            wineLotCloud.setSurface(wineLot.getSurface());
            wineLotCloud.setNumberWineStock(wineLot.getNumberWineStock());
            wineLotCloud.setPicture(wineLot.getPicture());
            wineLotCloud.setLatitude(wineLot.getLatitude());
            wineLotCloud.setLongitude(wineLot.getLongitude());
            wineLotCloud.setOrientationid(wineLot.getOrientationid());
            wineLotCloud.setWineVariety(wineVarietyCloud);

            new WineLotAsyncTask(wineLotCloud).execute();
        }
    }

    private static void sendJob(){

        Log.i("CLOUD MANAGER", "Sending job to the cloud");

        List<Job> jobList = MainActivity.jobDataSource.getAllJobs();

        for (Job job : jobList) {
            com.luca.flavien.wineyardmanager.entities.jobApi.model.Job jobCloud
                    = new com.luca.flavien.wineyardmanager.entities.jobApi.model.Job();
            com.luca.flavien.wineyardmanager.entities.jobApi.model.WineLot wineLotCloud
                    = new com.luca.flavien.wineyardmanager.entities.jobApi.model.WineLot();
            com.luca.flavien.wineyardmanager.entities.jobApi.model.WineVariety wineVarietyCloud
                    = new com.luca.flavien.wineyardmanager.entities.jobApi.model.WineVariety();
            com.luca.flavien.wineyardmanager.entities.jobApi.model.Worker workerCloud
                    = new com.luca.flavien.wineyardmanager.entities.jobApi.model.Worker();

            wineVarietyCloud.setId((long)job.getWinelot().getWineVariety().getId());
            wineVarietyCloud.setName(job.getWinelot().getWineVariety().getName());

            wineLotCloud.setId((long)job.getWinelot().getId());
            wineLotCloud.setName(job.getWinelot().getName());
            wineLotCloud.setSurface(job.getWinelot().getSurface());
            wineLotCloud.setNumberWineStock(job.getWinelot().getNumberWineStock());
            wineLotCloud.setPicture(job.getWinelot().getPicture());
            wineLotCloud.setLatitude(job.getWinelot().getLatitude());
            wineLotCloud.setLongitude(job.getWinelot().getLongitude());
            wineLotCloud.setOrientationid(job.getWinelot().getOrientationid());
            wineLotCloud.setWineVariety(wineVarietyCloud);

            workerCloud.setId((long)job.getWorker().getId());
            workerCloud.setFirstName(job.getWorker().getFirstName());
            workerCloud.setLastName(job.getWorker().getLastName());
            workerCloud.setPhone(job.getWorker().getPhone());
            workerCloud.setMail(job.getWorker().getMail());


            jobCloud.setId((long)job.getId());
            jobCloud.setDeadline(job.getDeadline());
            jobCloud.setDescription(job.getDescription());
            jobCloud.setWinelot(wineLotCloud);
            jobCloud.setWorker(workerCloud);

            boolean done = true;

            new JobAsyncTask(jobCloud).execute();
        }
    }

    public static void getWineVarietyFromAppEngine(List<com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety> wineVarietyList){

        Log.i("CLOUD MANAGER", "Getting wine variety from the cloud");

        SQLiteDatabase db = MainActivity.wineVarietyDataSource.getDb();

        db.delete(Contract.WineVarietyEntry.TABLE_WINEVARIETY, null, null);

        for (com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety wineVarietyCloud : wineVarietyList) {
            WineVariety wineVarietyTemp = new WineVariety();
            wineVarietyTemp.setName(wineVarietyCloud.getName());

            MainActivity.wineVarietyDataSource.createWineVariety(wineVarietyTemp);
        }
    }

    public static void getWorkerFromAppEngine(List<com.luca.flavien.wineyardmanager.entities.workerApi.model.Worker> workerList){

        Log.i("CLOUD MANAGER", "Getting worker from the cloud");

        SQLiteDatabase db = MainActivity.workerDataSource.getDb();

        db.delete(Contract.WorkerEntry.TABLE_WORKER, null, null);

        for (com.luca.flavien.wineyardmanager.entities.workerApi.model.Worker workerCloud : workerList) {
            Worker workerTemp = new Worker();
            workerTemp.setFirstName(workerCloud.getFirstName());
            workerTemp.setLastName(workerCloud.getLastName());
            workerTemp.setMail(workerCloud.getMail());
            workerTemp.setPhone(workerCloud.getPhone());

            MainActivity.workerDataSource.createWorker(workerTemp);
        }
    }

    public static void getWinlotFromAppEngine(List<com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineLot> winelotList){

        Log.i("CLOUD MANAGER", "Getting wine lot from the cloud");

        SQLiteDatabase db = MainActivity.wineLotDataSource.getDb();

        db.delete(Contract.WineLotEntry.TABLE_WINELOT, null, null);

        for (com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineLot winlotCloud : winelotList) {
            WineLot winelotTemp = new WineLot();
            WineVariety wineVarietyTemp = new WineVariety();

            wineVarietyTemp.setId(Integer.parseInt(winlotCloud.getWineVariety().getId() + ""));
            wineVarietyTemp.setName(winlotCloud.getWineVariety().getName());

            winelotTemp.setName(winlotCloud.getName());
            winelotTemp.setNumberWineStock(winlotCloud.getNumberWineStock());
            winelotTemp.setSurface(winlotCloud.getSurface());
            winelotTemp.setLatitude(winlotCloud.getLatitude());
            winelotTemp.setLongitude(winlotCloud.getLongitude());
            winelotTemp.setOrientationid(winlotCloud.getOrientationid());
            winelotTemp.setPicture(winlotCloud.getPicture());
            winelotTemp.setWineVariety(wineVarietyTemp);

            MainActivity.wineLotDataSource.createWineLot(winelotTemp);
        }

    }

    public static void getJobFromAppEngine(List<com.luca.flavien.wineyardmanager.entities.jobApi.model.Job> jobList){

        Log.i("CLOUD MANAGER", "Getting job from the cloud");

        SQLiteDatabase db = MainActivity.jobDataSource.getDb();

        db.delete(Contract.JobEntry.TABLE_JOB, null, null);

        for (com.luca.flavien.wineyardmanager.entities.jobApi.model.Job jobCloud : jobList) {
            Job jobTemp = new Job();
            Worker workerTemp = new Worker();
            WineLot wineLotTemp = new WineLot();
            WineVariety wineVarietyTemp = new WineVariety();

            workerTemp.setId(Integer.parseInt(jobCloud.getWorker().getId()+""));
            workerTemp.setFirstName(jobCloud.getWorker().getFirstName());
            workerTemp.setLastName(jobCloud.getWorker().getLastName());
            workerTemp.setMail(jobCloud.getWorker().getMail());
            workerTemp.setPhone(jobCloud.getWorker().getPhone());

            wineVarietyTemp.setId(Integer.parseInt(jobCloud.getWinelot().getWineVariety().getId() + ""));
            wineVarietyTemp.setName(jobCloud.getWinelot().getWineVariety().getName());

            wineLotTemp.setId(Integer.parseInt(jobCloud.getWinelot().getId()+""));
            wineLotTemp.setName(jobCloud.getWinelot().getName());
            wineLotTemp.setNumberWineStock(jobCloud.getWinelot().getNumberWineStock());
            wineLotTemp.setSurface(jobCloud.getWinelot().getSurface());
            wineLotTemp.setLatitude(jobCloud.getWinelot().getLatitude());
            wineLotTemp.setLongitude(jobCloud.getWinelot().getLongitude());
            wineLotTemp.setOrientationid(jobCloud.getWinelot().getOrientationid());
            wineLotTemp.setPicture(jobCloud.getWinelot().getPicture());
            wineLotTemp.setWineVariety(wineVarietyTemp);

            jobTemp.setDescription(jobCloud.getDescription());
            jobTemp.setDeadline(jobCloud.getDeadline());
            jobTemp.setWorker(workerTemp);
            jobTemp.setWinelot(wineLotTemp);

            MainActivity.jobDataSource.createJob(jobTemp);
        }
    }
}
