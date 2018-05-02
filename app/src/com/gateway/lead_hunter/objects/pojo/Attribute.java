package com.gateway.lead_hunter.objects.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gateway.lead_hunter.objects.ShowObject;

import java.io.Serializable;

/**
 * Created by psyfu on 3/17/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Attribute implements Serializable {

    @JsonProperty(ShowObject.ATTRIBUTES_TYPE)
    private String type;

    @JsonProperty(ShowObject.ATTRIBUTES_URL)
    private String url;

    Attribute(){

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
