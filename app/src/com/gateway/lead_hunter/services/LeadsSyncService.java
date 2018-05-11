package com.gateway.lead_hunter.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gateway.lead_hunter.R;
import com.gateway.lead_hunter.broadcast.LeadSyncAlarmReceiver;
import com.gateway.lead_hunter.objects.LeadObject;
import com.gateway.lead_hunter.utils.Utils;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;
import com.salesforce.androidsdk.smartsync.manager.SyncManager;
import com.salesforce.androidsdk.smartsync.target.SyncUpTarget;
import com.salesforce.androidsdk.smartsync.util.SyncOptions;
import com.salesforce.androidsdk.smartsync.util.SyncState;

import org.json.JSONException;

import java.util.Arrays;
import java.util.List;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class LeadsSyncService extends IntentService {

    private static final Integer LIMIT = 10000;
    private static final Integer ALARM_SERVICE_CODE = 23145;
    private static final String TAG = LeadsSyncService.class.getName();

    private UserAccount account;
    private SmartStore smartStore;
    private SyncManager syncMgr;
    private AlarmManager alarmMgr;

    public LeadsSyncService() {
        super(LeadsSyncService.class.getName());
        account = SmartSyncSDKManager.getInstance().getUserAccountManager().getCurrentUser();
        smartStore = SmartSyncSDKManager.getInstance().getSmartStore(account);
        syncMgr = SyncManager.getInstance(account);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if(Utils.isInternetAvailable(getApplicationContext())) {
            showToast(this, getString(R.string.toast_started_lead_service));
            this.syncUp(intent);
        }else {
            showToast(this, getString(R.string.you_are_offline));
            startSyncAlarmService();
        }
    }

    /**
     * Pushes local changes up to the server.
     */
    public synchronized void syncUp(Intent intent) {

        List<String> listOfFields = Arrays.asList(LeadObject.LEAD_FIELDS_SYNC_UP);
        if (intent.getStringExtra("mode")!= null){
            listOfFields = Arrays.asList(LeadObject.LEAD_FIELDS_UPDATE);
        }

        smartStore.registerSoup(LeadObject.LEAD_SOUP, LeadObject.LEAD_INDEX_SPEC);
        final SyncUpTarget target = new SyncUpTarget();
        final SyncOptions options = SyncOptions.optionsForSyncUp(listOfFields,
                SyncState.MergeMode.OVERWRITE);

        final SyncManager.SyncUpdateCallback callback = new SyncManager.SyncUpdateCallback() {

            @Override
            public void onUpdate(SyncState sync) {

                if (sync.getStatus().equals(SyncState.Status.DONE)) {
                    stopSyncAlarmService();
                    showToast(getApplication(),getString(R.string.toast_finished_leads_upload));
                }else if(sync.getStatus().equals(SyncState.Status.FAILED)){
                    showToast(getApplication(), getString(R.string.toast_error));
                    startSyncAlarmService();
                }
            }
        };

        try {
            syncMgr.syncUp(target, options, LeadObject.LEAD_SOUP, callback);
        } catch (JSONException je) {
            Log.e(TAG, "JSONException occurred while parsing", je);
        } catch (SyncManager.SmartSyncException se) {
            Log.e(TAG, "SmartSyncException occurred while attempting to sync up", se);
        }
    }

    private void startSyncAlarmService(){
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent startTimeSyncService = new Intent(getApplicationContext(), LeadSyncAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_SERVICE_CODE, startTimeSyncService, 0);

        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                AlarmManager.INTERVAL_HOUR,
                AlarmManager.INTERVAL_HOUR,
                alarmIntent);
    }

    private void stopSyncAlarmService(){
        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent startTimeSyncService = new Intent(getApplicationContext(), LeadSyncAlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), ALARM_SERVICE_CODE, startTimeSyncService, 0);
        alarmMgr.cancel(alarmIntent);
    }

    private void showToast(final Context context, final String text){
        Handler h = new Handler(context.getMainLooper());

        h.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
