package com.gateway.lead_hunter.async;

import android.os.AsyncTask;
import android.util.Log;

import com.gateway.lead_hunter.objects.pojo.Show;
import com.gateway.lead_hunter.utils.DBManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ShowLoader extends AsyncTask<String, Integer, List<Show>> {

    public AsyncResponse delegate = null;

    @Override
    protected List<Show> doInBackground(String... params) {
        try {
            return DBManager.getInstance().getAllShows();
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException io) {
            Log.e(TAG, io.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(List<Show> result) {
        delegate.processListOfShows(result);
    }
}
