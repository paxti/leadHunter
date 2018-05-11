package com.gateway.lead_hunter.async;

import android.os.AsyncTask;
import android.util.Log;

import com.gateway.lead_hunter.objects.pojo.Lead;
import com.gateway.lead_hunter.objects.pojo.Show;
import com.gateway.lead_hunter.utils.DBManager;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class LeadsLoader extends AsyncTask<String, Integer, List<Lead>> {
    @Override
    protected List<Lead> doInBackground(String... params) {
        try {
            return DBManager.getInstance().getAllLeadsForShow(params[0]);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        } catch (IOException io) {
            Log.e(TAG, io.getMessage());
        }

        return null;
    }
}