package com.gateway.lead_hunter.services;

import android.app.AlarmManager;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gateway.lead_hunter.objects.ShowObject;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;
import com.salesforce.androidsdk.smartsync.manager.SyncManager;
import com.salesforce.androidsdk.smartsync.target.SoqlSyncDownTarget;
import com.salesforce.androidsdk.smartsync.target.SyncDownTarget;
import com.salesforce.androidsdk.smartsync.util.SOQLBuilder;
import com.salesforce.androidsdk.smartsync.util.SyncOptions;
import com.salesforce.androidsdk.smartsync.util.SyncState;

import org.json.JSONException;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * helper methods.
 */
public class ShowsSyncService extends IntentService {

    private static final Integer LIMIT = 10000;
    private static final Integer ALARM_SERVICE_CODE = 23145;
    private static final String TAG = ShowsSyncService.class.getName();

    private UserAccount account;
    private SmartStore smartStore;
    private SyncManager syncMgr;
    private AlarmManager alarmMgr;

    public ShowsSyncService() {
        super(ShowsSyncService.class.getName());
        account = SmartSyncSDKManager.getInstance().getUserAccountManager().getCurrentUser();
        smartStore = SmartSyncSDKManager.getInstance().getSmartStore(account);
        syncMgr = SyncManager.getInstance(account);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        this.syncDown();
    }

    /**
     * Pulls the latest records from the server.
     */
    public synchronized void syncDown() {
        final SyncManager.SyncUpdateCallback callback = new SyncManager.SyncUpdateCallback() {

            @Override
            public void onUpdate(SyncState sync) {
                if (SyncState.Status.DONE.equals(sync.getStatus())) {
                    showToast(getApplication(), "Test");
                }
            }
        };
        try {
            final SyncOptions options = SyncOptions.optionsForSyncDown(SyncState.MergeMode.OVERWRITE);
            final String soqlQuery = SOQLBuilder.getInstanceWithFields(ShowObject.TIME_FIELDS_SYNC_DOWN)
                    .from(ShowObject.SHOW_SF_OBJECT)
                    .where(ShowObject.buildWhereRequest())
                    .limit(LIMIT).build();
            final SyncDownTarget target = new SoqlSyncDownTarget(soqlQuery);
            syncMgr.syncDown(target, options, ShowObject.SHOW_SOUP, callback);
        } catch (JSONException je) {
            Log.e(TAG, "JSONException occurred while parsing", je);
        } catch (SyncManager.SmartSyncException se) {
            Log.e(TAG, "SmartSyncException occurred while attempting to sync down", se);
        }
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
