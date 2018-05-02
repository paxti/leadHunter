package com.gateway.lead_hunter.objects;

import com.salesforce.androidsdk.smartstore.store.IndexSpec;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;
import com.salesforce.androidsdk.smartsync.manager.SyncManager;
import com.salesforce.androidsdk.smartsync.model.SalesforceObject;
import com.salesforce.androidsdk.smartsync.util.Constants;

import org.json.JSONObject;

public class ShowObject extends SalesforceObject {

    private boolean isLocallyModified;

    public static final String SHOW_SOUP = "Shows";
    public static final String SHOW_SF_OBJECT = "Show_and_Event__c";

    public static final String NAME = "Name";
    public static final String START_DATE = "Start_Date__c";
    public static final String END_DATE = "End_Date__c";
    public static final String CITY = "City__c";
    public static final String RELATED_CONVENTION_CENTER = "Convention_Center__r";
    public static final String CONVENTION_CENTER_NAME = "Name";

    public static final String ATTRIBUTES = "attributes";
    public static final String ATTRIBUTES_TYPE = "type";
    public static final String ATTRIBUTES_URL = "url";

    public static final String LOCAL = "__local__";
    public static final String LOCALY_CREATED = "__locally_created__";
    public static final String LOCALY_UPDATED = "__locally_updated__";
    public static final String LOCALY_DELETED = "__locally_deleted__";

    public static final String SHOW_STATUS = "order__r.Status";

    public static IndexSpec[] TIMES_INDEX_SPEC = {
        new IndexSpec(Constants.ID, SmartStore.Type.string),
        new IndexSpec(NAME, SmartStore.Type.string),
        new IndexSpec(RELATED_CONVENTION_CENTER +  "." + CONVENTION_CENTER_NAME, SmartStore.Type.string),
        new IndexSpec(CITY, SmartStore.Type.string),
        new IndexSpec(START_DATE, SmartStore.Type.string),
        new IndexSpec(END_DATE, SmartStore.Type.string),
        new IndexSpec(LOCAL, SmartStore.Type.string)
    };

    public static final String[] TIME_FIELDS_SYNC_UP = { };

    public static final String[] TIME_FIELDS_UPDATE = { };

    public static final String[] TIME_FIELDS_SYNC_DOWN = {
            Constants.ID,
            RELATED_CONVENTION_CENTER +  "." + CONVENTION_CENTER_NAME,
            CITY,
            START_DATE,
            END_DATE,
            Constants.LAST_MODIFIED_DATE
    };



    /**
     * Parameterized constructor.
     *
     * @param object Raw data for object.
     */
    public ShowObject(JSONObject object) {
        super(object);

        objectType = "ShowObject";
        objectId = object.optString(Constants.ID);
        name = object.optString(NAME) + " " + object.optString(Constants.ID) ;

        isLocallyModified = false;
    }

    public static String buildWhereRequest(){
        return "Sync_in_mobile_app__c = TRUE";
    }

    /**
     * Returns whether the times has been locally modified or not.
     *
     * @return True - if the contact has been locally modified, False - otherwise.
     */
    public boolean isLocallyModified() {
        return isLocallyModified;
    }

}
