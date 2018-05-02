package com.gateway.lead_hunter.objects.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.lead_hunter.objects.ShowObject;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.util.Constants;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Show implements Serializable {

    @JsonProperty(SmartStore.SOUP_ENTRY_ID)
    private Long entyId;

    @JsonProperty(Constants.ID)
    private String id;

    @JsonProperty(ShowObject.CITY)
    private String city;

    @JsonProperty(ShowObject.CONVENTION_CENTER_NAME)
    private String conventionCenter;

    @JsonProperty(ShowObject.START_DATE)
    @JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT" )
    private Date startDate;

    @JsonProperty(ShowObject.END_DATE)
    @JsonFormat(locale = "en", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT" )
    private Date endDate;

    @JsonProperty(ShowObject.LOCAL)
    private Boolean local;

    @JsonProperty(ShowObject.LOCALY_CREATED)
    private Boolean locallyCreated;

    @JsonProperty(ShowObject.LOCALY_UPDATED)
    private Boolean locallyUpdated;

    @JsonProperty(ShowObject.LOCALY_DELETED)
    private Boolean locallyDeleted;

    /**
     * Setters
     */
    public void setEntyId(Long entyId) {
        this.entyId = entyId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setConventionCenter(String conventionCenter) {
        this.conventionCenter = conventionCenter;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public void setLocallyCreated(Boolean locallyCreated) {
        this.locallyCreated = locallyCreated;
    }

    public void setLocallyUpdated(Boolean locallyUpdated) {
        this.locallyUpdated = locallyUpdated;
    }

    public void setLocallyDeleted(Boolean locallyDeleted) {
        this.locallyDeleted = locallyDeleted;
    }

    /**
     * Getters
     */

    public Long getEntyId() {
        return entyId;
    }

    public String getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public String getConventionCenter() {
        return conventionCenter;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Boolean getLocal() {
        return local;
    }

    public Boolean getLocallyCreated() {
        return locallyCreated;
    }

    public Boolean getLocallyUpdated() {
        return locallyUpdated;
    }

    public Boolean getLocallyDeleted() {
        return locallyDeleted;
    }

    public String getShowDates() {
        return String.format(
                "%s - %s",
                new SimpleDateFormat("MMM d").format(this.startDate),
                new SimpleDateFormat("MMM d").format(this.endDate)
        );
    }

}
