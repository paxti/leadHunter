package com.gateway.lead_hunter.utils;

import android.content.Context;
import android.util.Log;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.gateway.lead_hunter.objects.LeadObject;
import com.gateway.lead_hunter.objects.ShowObject;
import com.gateway.lead_hunter.objects.pojo.Lead;
import com.gateway.lead_hunter.objects.pojo.Show;
import com.salesforce.androidsdk.accounts.UserAccount;
import com.salesforce.androidsdk.smartstore.store.QuerySpec;
import com.salesforce.androidsdk.smartstore.store.SmartSqlHelper;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.app.SmartSyncSDKManager;
import com.salesforce.androidsdk.smartsync.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DBManager {

    final public static int PAGE_SIZE = 2000;

    private UserAccount account;
    private SmartStore smartStore;
    private Context context;

    private ObjectMapper mapper;

    private static DBManager ourInstance = new DBManager();

    public static DBManager getInstance() {
        return ourInstance;
    }

    private DBManager() {
        this.account = SmartSyncSDKManager.getInstance().getUserAccountManager().getCurrentUser();
        this.smartStore = SmartSyncSDKManager.getInstance().getSmartStore(account);
        this.context = SmartSyncSDKManager.getInstance().getAppContext();

        this.mapper = new ObjectMapper();
    }

    public Show saveShow(Show showEntry) throws JSONException, IOException {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectReader jsonReader = mapper.readerFor(Show.class);

        JSONObject objectToSave = new JSONObject(mapper.valueToTree(showEntry).toString());
        JSONObject savedObject = smartStore.create(ShowObject.SHOW_SOUP, objectToSave);
        return (Show) jsonReader.readValue(savedObject.toString());
    }

    public List<Show> getAllShows() throws JSONException, IOException {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ObjectReader jsonReader = mapper.readerFor(Show.class);
        JSONArray res = getAllInSoup(ShowObject.SHOW_SOUP);
        List<Show> shows = new ArrayList<>();

        for (int i = 0; i < res.length(); i++){
            shows.add((Show) jsonReader.readValue(res.getJSONArray(i).getJSONObject(0).toString()));
        }

        return  shows;
    }

    public List<Lead> getAllLeadsForShow(String showEntryId) throws JSONException, IOException {

        QuerySpec queryLeadsByShow = QuerySpec.buildExactQuerySpec(LeadObject.LEAD_SOUP,
                LeadObject.SHOW_ENTRY_ID,
                showEntryId,
                SmartStore.SOUP_LAST_MODIFIED_DATE ,
                QuerySpec.Order.descending,
                1000);


        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ObjectReader jsonReader = mapper.readerFor(Lead.class);
        JSONArray res = smartStore.query(queryLeadsByShow, 0);
        List<Lead> leads = new ArrayList<>();

        for (int i = 0; i < res.length(); i++){
            leads.add((Lead) jsonReader.readValue(res.getJSONObject(i).toString()));
        }

        return  leads;
    }

    public List<Lead> getAllLeads() throws JSONException, IOException {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ObjectReader jsonReader = mapper.readerFor(Lead.class);
        JSONArray res = getAllInSoup(LeadObject.LEAD_SOUP);
        List<Lead> leads = new ArrayList<>();

        for (int i = 0; i < res.length(); i++){
            leads.add((Lead) jsonReader.readValue(res.getJSONArray(i).getJSONObject(0).toString()));
        }

        return  leads;
    }

    private JSONArray getAllInSoup(String soup) throws JSONException, IOException {
        String getAllRequest = String.format("SELECT {%1$s:%2$s} FROM {%1$s}",
                soup,
                SmartSqlHelper.SOUP);
        return smartStore.query(QuerySpec.buildSmartQuerySpec(getAllRequest, PAGE_SIZE), 0);
    }

    public Lead getLead(Long leadEntryId) throws JSONException, IOException {
        QuerySpec queryLeadsByShow = QuerySpec.buildExactQuerySpec(LeadObject.LEAD_SOUP,
                SmartStore.SOUP_ENTRY_ID,
                leadEntryId.toString(),
                null,
                null,
                1);

        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ObjectReader jsonReader = mapper.readerFor(Lead.class);
        JSONArray res = smartStore.query(queryLeadsByShow, 0);

        return (Lead) jsonReader.readValue(res.getJSONObject(0).toString());
    }

    public Lead saveLead(Lead leadEntry) throws JSONException, IOException {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectReader jsonReader = mapper.readerFor(Lead.class);

        JSONObject objectToSave = new JSONObject(mapper.valueToTree(leadEntry).toString());
        JSONObject savedObject = smartStore.create(LeadObject.LEAD_SOUP, objectToSave);
        return (Lead) jsonReader.readValue(savedObject.toString());
    }

    public Lead updateLead(Lead leadEntry, Long entryId) throws JSONException, IOException {
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        ObjectReader jsonReader = mapper.readerFor(Lead.class);

        JSONObject objectToSave = new JSONObject(mapper.valueToTree(leadEntry).toString());
        JSONObject updatedObject = smartStore.update(LeadObject.LEAD_SOUP, objectToSave, entryId);
        return (Lead) jsonReader.readValue(updatedObject.toString());
    }

    public Lead createLead(String showEntryId, String firstName, String lastName,
                           String company, String email,
                           String phone, String notes) throws JSONException, IOException{

        Lead newEntry = new Lead(showEntryId, firstName, lastName, email, company, phone, notes);
        return saveLead(newEntry);
    }

}
