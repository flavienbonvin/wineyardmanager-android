package com.luca.flavien.wineyardmanager.cloud;

import android.util.Log;
import android.widget.Toast;

import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.db.object.WineVariety;
import com.luca.flavien.wineyardmanager.db.object.Worker;
import com.luca.flavien.wineyardmanager.db.object.WineLot;
import com.luca.flavien.wineyardmanager.db.object.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavien on 11.05.17.
 */

public class CloudManager {

    public static void SendToCloud(){

        sendWineVariety();
        sendWorker();
        sendWineVariety();
        sendJob();
        Log.d("SEND", "All data synced to the cloud");

    }

    private static void sendWineVariety() {
        List<WineVariety> wineVarietyList = MainActivity.wineVarietyDataSource.getAllWineVarieties();

        for (WineVariety wineVariety : wineVarietyList) {
            com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety wineVarietyCloud
                    = new com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety();

            wineVarietyCloud.setId((long)wineVariety.getId());
            wineVarietyCloud.setName(wineVariety.getName());

            new WineVarietyAsyncTask(wineVarietyCloud).execute();
        }
    }

    private static void sendWorker(){
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

    private static void sendWineLot(){
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
        }
    }

    private static void sendJob(){
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

            new JobAsyncTask(jobCloud).execute();
        }
    }
}
