package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResource implements Serializable {
    private static final long serialVersionUID = -6391138674183752385L;

    @JsonProperty(value = "AUTH_ID")
    private String authId;
    
    @JsonProperty(value = "AUTH_NM")
    private String authNm;
    
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

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthNm() {
        return authNm;
    }

    public void setAuthNm(String authNm) {
        this.authNm = authNm;
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
