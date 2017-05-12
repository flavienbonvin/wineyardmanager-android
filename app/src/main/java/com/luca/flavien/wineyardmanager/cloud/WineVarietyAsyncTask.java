package com.luca.flavien.wineyardmanager.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.luca.flavien.wineyardmanager.entities.wineVarietyApi.model.WineVariety;
import com.luca.flavien.wineyardmanager.entities.wineVarietyApi.WineVarietyApi;

/**
 * Created by flavien on 11.05.17.
 */

public class WineVarietyAsyncTask extends AsyncTask<Void, Void, List<WineVariety>> {
    private static WineVarietyApi wineVarietyApi = null;
    private static final String TAG = WineVarietyAsyncTask.class.getName();
    private WineVariety wineVariety;

    private long id = -1;

    public WineVarietyAsyncTask(){ id = -2;}

    public WineVarietyAsyncTask(WineVariety wineVariety){
        this.wineVariety = wineVariety;
    }

    public WineVarietyAsyncTask(long id){ this.id = id; }

    @Override
    protected List<WineVariety> doInBackground(Void... params) {

        if(wineVarietyApi == null){
            // Only do this once
            WineVarietyApi.Builder builder = new WineVarietyApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            wineVarietyApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(wineVariety != null){
                wineVarietyApi.insert(wineVariety).execute();
                Log.i(TAG, "insert wine variety " + wineVariety.getId() + " " + wineVariety.getName());
            }

            // and for instance return the list of all employees
            return wineVarietyApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<WineVariety>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<WineVariety> result){

        if(result != null) {

            CloudManager.getWineVarietyFromAppEngine(result);

            for (WineVariety  wineVariety : result) {
                Log.i(TAG,
                        "Name: " + wineVariety.getName());

            }
        }
    }
}
