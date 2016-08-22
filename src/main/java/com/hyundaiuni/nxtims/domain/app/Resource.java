package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Resource implements Serializable {
    private static final long serialVersionUID = -1061697241118943250L;

    @JsonProperty(value = "RESOURCE_LEVEL")
    private String resourceLevel;
    
    @JsonProperty(value = "RESOURCE_ID")
    private String resourceId;
    
    @JsonProperty(value = "RESOURCE_NM")
    private String resourceNm;
    
    @JsonProperty(value = "RESOURCE_URL")
    private String resourceUrl;
    
    @JsonProperty(value = "RESOURCE_TYPE")
    private String resourceType;
    
    @JsonProperty(value = "HTTP_METHOD")
    private String httpMethod;
    
    @JsonProperty(value = "SORT_ORDER")
    private int sortOrder;

    public String getResourceLevel() {
        return resourceLevel;
    }

    public void setResourceLevel(String resourceLevel) {
        this.resourceLevel = resourceLevel;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceNm() {
        return resourceNm;
    }

    public void setResourceNm(String resourceNm) {
        this.resourceNm = resourceNm;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
