package com.gateway.lead_hunter.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gateway.lead_hunter.services.LeadsSyncService;
import com.gateway.lead_hunter.utils.Utils;

public class LeadSyncAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if(Utils.isInternetAvailable(context)) {
            Intent service = new Intent(context, LeadsSyncService.class);
            context.startService(service);
        }
    }
}
