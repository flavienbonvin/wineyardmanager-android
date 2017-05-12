package com.luca.flavien.wineyardmanager.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.luca.flavien.wineyardmanager.entities.workerApi.WorkerApi;
import com.luca.flavien.wineyardmanager.entities.workerApi.model.Worker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavien on 11.05.17.
 */

public class WorkerAsyncTask extends AsyncTask<Void, Void, List<Worker>> {
    private static WorkerApi workerApi = null;
    private static final String TAG = WorkerAsyncTask.class.getName();
    private Worker worker;

    public WorkerAsyncTask(){}

    public WorkerAsyncTask(Worker worker){
        this.worker = worker;
    }

    private WorkerAsyncTask(long id){
        long id1 = id;
    }

    @Override
    protected List<Worker> doInBackground(Void... params) {

        if(workerApi == null){
            // Only do this once
            WorkerApi.Builder builder = new WorkerApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    // if you deploy on the cloud backend, use your app name
                    // such as https://<your-app-id>.appspot.com
                    .setRootUrl("https://cloudwineyard.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            workerApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(worker != null){
                workerApi.insert(worker).execute();
                Log.i(TAG, "insert worker");
            }

            // and for instance return the list of all employees
            return workerApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Worker> result){

        if(result != null) {

            CloudManager.getWorkerFromAppEngine(result);

            for (Worker  worker : result) {
                Log.i(TAG,
                        "Lastname: " + worker.getFirstName() +
                        " Firstname: " + worker.getLastName() +
                        " Mail: " + worker.getMail() +
                        " Phone: " + worker.getPhone());

            }
        }
    }
}
