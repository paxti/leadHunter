package com.gateway.lead_hunter.objects.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.lead_hunter.objects.LeadObject;
import com.gateway.lead_hunter.objects.ShowObject;
import com.salesforce.androidsdk.smartstore.store.SmartStore;
import com.salesforce.androidsdk.smartsync.util.Constants;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Lead {

    @JsonProperty(Constants.ID)
    private String id;

    @JsonProperty(SmartStore.SOUP_ENTRY_ID)
    private Long entyId;

    @JsonProperty(LeadObject.SHOW_ENTRY_ID)
    private String showEntryId;

    @JsonProperty(LeadObject.FIRST_NAME)
    private String firstName;

    @JsonProperty(LeadObject.LAST_NAME)
    private String lastName;

    @JsonProperty(LeadObject.COMPANY)
    private String company;

    @JsonProperty(LeadObject.EMAIL)
    private String email;

    @JsonProperty(LeadObject.PHONE)
    private String phone;

    @JsonProperty(LeadObject.NOTES)
    private String notes;

    @JsonProperty(ShowObject.LOCAL)
    private Boolean local;

    @JsonProperty(ShowObject.LOCALY_CREATED)
    private Boolean locallyCreated;

    @JsonProperty(ShowObject.LOCALY_UPDATED)
    private Boolean locallyUpdated;

    @JsonProperty(ShowObject.LOCALY_DELETED)
    private Boolean locallyDeleted;

    public Lead(){}

    public Lead (String showEntryId, String firstName, String lastName,
                 String email, String company, String phone,
                 String notes) {

        this.showEntryId = showEntryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
        this.phone = phone;
        this.notes = notes;
    }

    public Lead (String firstName, String lastName,
                 String email, String company, String phone) {

        this.showEntryId = showEntryId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
        this.phone = phone;
    }

    public Lead create(){
        setLocal(true);
        setLocallyCreated(true);
        return this;
    }

    public void setLocallyCreated(Boolean locallyCreated) {
        this.locallyCreated = locallyCreated;
    }

    public void setLocal(Boolean local) {
        this.local = local;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getEntyId() {
        return entyId;
    }

    public void setEntyId(Long entyId) {
        this.entyId = entyId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public void setLocallyUpdated(Boolean locallyUpdated) {
        this.locallyUpdated = locallyUpdated;
    }

    public Boolean getLocallyDeleted() {
        return locallyDeleted;
    }

    public void setLocallyDeleted(Boolean locallyDeleted) {
        this.locallyDeleted = locallyDeleted;
    }

    public String getShowEntryId() {
        return showEntryId;
    }

    public void setShowEntryId(String showEntryId) {
        this.showEntryId = showEntryId;
    }
}
