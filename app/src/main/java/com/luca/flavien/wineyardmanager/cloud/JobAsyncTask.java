package com.luca.flavien.wineyardmanager.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.luca.flavien.wineyardmanager.MainActivity;
import com.luca.flavien.wineyardmanager.db.Contract;
import com.luca.flavien.wineyardmanager.entities.jobApi.JobApi;
import com.luca.flavien.wineyardmanager.entities.jobApi.model.Job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

/**
 * Created by flavien on 11.05.17.
 */

public class JobAsyncTask extends AsyncTask<Void, Void, List<Job>> {
    private static JobApi jobApi = null;
    private static final String TAG = JobAsyncTask.class.getName();
    private Job job;

    private long id = -1;

    public JobAsyncTask(){}

    public JobAsyncTask(Job job){
        this.job = job;
    }

    public JobAsyncTask(long id){
        this.id = id;
    }

    @Override
    protected List<Job> doInBackground(Void... params) {

        if(jobApi == null){
            // Only do this once
            JobApi.Builder builder = new JobApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            jobApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(job != null){
                jobApi.insert(job).execute();
                Log.i(TAG, "insert job");
            }else if(id == -2) {
                List<Job> jobList = jobApi.list().execute().getItems();
                if(jobList != null){
                    CloudManager.getJobFromAppEngine(jobList);
                }else {
                    MainActivity.jobDataSource.getDb().delete(Contract.JobEntry.TABLE_JOB, null, null);
                }
            }else if(id != -1){
                Log.i(TAG, "delete job ");
                jobApi.remove(id+1).execute();
                return null;
            }
            // and for instance return the list of all employees
            return jobApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<Job> result){

        if(result != null) {

            //CloudManager.getJobFromAppEngine(result);

            for (Job  job : result) {
                Log.i(TAG,
                        "onPostExecute" +
                        "Description: " + job.getDescription() +
                        " Vinelot: " + job.getWinelot().getName() +
                        " Deadline: " + job.getDeadline() +
                        " Firstname: " + job.getWorker().getFirstName() +
                        " Lastname: " + job.getWorker().getLastName());

            }
        }
    }
}
