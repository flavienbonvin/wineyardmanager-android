package com.luca.flavien.wineyardmanager.cloud;

import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.luca.flavien.wineyardmanager.entities.wineLotApi.WineLotApi;
import com.luca.flavien.wineyardmanager.entities.wineLotApi.model.WineLot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by flavien on 11.05.17.
 */

public class WineLotAsyncTask extends AsyncTask<Void, Void, List<WineLot>> {
    private static WineLotApi wineLotApi = null;
    private static final String TAG = WineLotAsyncTask.class.getName();
    private WineLot wineLot;

    private long id = -1;

    public WineLotAsyncTask(){}

    public WineLotAsyncTask(WineLot wineLot){
        this.wineLot = wineLot;
    }

    public WineLotAsyncTask(long id){ this.id = id; }

    @Override
    protected List<WineLot> doInBackground(Void... params) {

        if(wineLotApi == null){
            // Only do this once
            WineLotApi.Builder builder = new WineLotApi.Builder(AndroidHttp.newCompatibleTransport(),
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
            wineLotApi = builder.build();
        }

        try{
            // Call here the wished methods on the Endpoints
            // For instance insert
            if(wineLot != null){
                wineLotApi.insert(wineLot).execute();
                Log.i(TAG, "insert winelot");
            }else if(id != -1){
                Log.i(TAG, "delete winelot ");
                wineLotApi.remove(id).execute();
                return null;
            }
            // and for instance return the list of all employees
            return wineLotApi.list().execute().getItems();

        } catch (IOException e){
            Log.e(TAG, e.toString());
            return new ArrayList<>();
        }
    }

    //This method gets executed on the UI thread - The UI can be manipulated directly inside
    //of this method
    @Override
    protected void onPostExecute(List<WineLot> result){

        if(result != null) {

            CloudManager.getWinlotFromAppEngine(result);

            for (WineLot  wineLot : result) {
                Log.i(TAG,
                    "Name: " + wineLot.getName() +
                    " ¦ Surface: " + wineLot.getSurface() +
                    " ¦ Wine stock: " + wineLot.getNumberWineStock() +
                    " ¦ Orientation id: " + wineLot.getOrientationid() +
                    " ¦ Variety: " + wineLot.getWineVariety().getName());

            }
        }
    }
}
