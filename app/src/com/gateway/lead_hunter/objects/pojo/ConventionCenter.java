package com.gateway.lead_hunter.objects.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.lead_hunter.objects.ShowObject;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConventionCenter implements Serializable {

    @JsonProperty(ShowObject.CONVENTION_CENTER_NAME)
    private String name;

    @JsonProperty(ShowObject.ATTRIBUTES)
    private Attribute attribute;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public void setAttribute(Attribute attribute) {
        this.attribute = attribute;
    }

    ConventionCenter(){

    }
}