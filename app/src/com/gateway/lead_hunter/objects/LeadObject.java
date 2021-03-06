package com.gateway.lead_hunter.objects;

import com.salesforce.androidsdk.smartstore.store.IndexSpec;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.manager.SyncManager;
import com.salesforce.androidsdk.smartsync.model.SalesforceObject;
import com.salesforce.androidsdk.smartsync.target.SyncTarget;
import com.salesforce.androidsdk.smartsync.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class LeadObject extends SalesforceObject {

    private final boolean isLocallyModified;
    private final boolean isLocallyCreated;
    private final boolean isLocallyUpdated;
    private final boolean isLocallyDeleted;

    public static final String LEAD_SOUP = "Leads";
    public static final String LEAD_SF_OBJECT = "Lead";

    public static final String SHOW_ENTRY_ID = "ShowEntryId";
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String EMAIL = "Email";
    public static final String COMPANY = "Company";
    public static final String PHONE = "Phone";
    public static final String TRADE_SHOWS_ATTENDED = "Trade_Shows_Attended__c";
    public static final String NOTES = "Description";

    public static final String OBJECT_TYPE_KEY = "type";
    public static final String ATTRIBUTES = "attributes";
    public static final String LOCAL = "__local__";
    public static final String LOCALY_CREATED = "__locally_created__";
    public static final String LOCALY_UPDATED = "__locally_updated__";
    public static final String LOCALY_DELETED = "__locally_deleted__";

    public static IndexSpec[] LEAD_INDEX_SPEC = {
            new IndexSpec(Constants.ID, SmartStore.Type.string),
            new IndexSpec(SHOW_ENTRY_ID, SmartStore.Type.string),
            new IndexSpec(FIRST_NAME, SmartStore.Type.string),
            new IndexSpec(LAST_NAME, SmartStore.Type.string),
            new IndexSpec(EMAIL, SmartStore.Type.string),
            new IndexSpec(COMPANY, SmartStore.Type.string),
            new IndexSpec(PHONE, SmartStore.Type.string),
            new IndexSpec(TRADE_SHOWS_ATTENDED, SmartStore.Type.string),
            new IndexSpec(NOTES, SmartStore.Type.string),
            new IndexSpec(LOCAL, SmartStore.Type.string)
    };

    public static final String[] LEAD_FIELDS_SYNC_UP = {
            FIRST_NAME,
            LAST_NAME,
            EMAIL,
            COMPANY,
            PHONE,
            TRADE_SHOWS_ATTENDED,
            NOTES,
            Constants.LAST_MODIFIED_DATE
    };

    public static final String[] LEAD_FIELDS_UPDATE = {
            FIRST_NAME,
            LAST_NAME,
            EMAIL,
            COMPANY,
            PHONE,
            TRADE_SHOWS_ATTENDED,
            NOTES,
            Constants.LAST_MODIFIED_DATE
    };

    public static final String[] LEAD_FIELDS_SYNC_DOWN = { };

    public LeadObject(JSONObject object) {
        super(object);

        objectType = "LeadObject";
        objectId = object.optString(Constants.ID);
        name = object.optString(EMAIL) + " " + object.optString(Constants.ID) ;

        isLocallyCreated = object.optBoolean(SyncTarget.LOCALLY_CREATED);
        isLocallyDeleted = object.optBoolean(SyncTarget.LOCALLY_DELETED);
        isLocallyUpdated = object.optBoolean(SyncTarget.LOCALLY_UPDATED);
        isLocallyModified = isLocallyCreated || isLocallyUpdated || isLocallyDeleted;
    }

    public static JSONObject createLead(String showEntryId, String firstName, String lastName,
                                        String email, String company, String phone,
                                        String notes) throws JSONException {

        JSONObject object = new JSONObject();

        JSONObject additionalInfo = new JSONObject();
        additionalInfo.put(OBJECT_TYPE_KEY, LEAD_SF_OBJECT);

        object.put(Constants.ID, String.valueOf(System.currentTimeMillis()));
        object.put(SHOW_ENTRY_ID, showEntryId);
        object.put(FIRST_NAME, firstName);
        object.put(LAST_NAME, lastName);
        object.put(EMAIL, email);
        object.put(COMPANY, company);
        object.put(PHONE, phone);
        object.put(NOTES, notes);
        object.put(LOCAL, true);
        object.put(LOCALY_CREATED, true);
        object.put(LOCALY_UPDATED, false);
        object.put(LOCALY_DELETED, false);
        object.put(ATTRIBUTES, additionalInfo);

        return object;
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
