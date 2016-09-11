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
    
    @JsonProperty(value = "RESOURCE_TYPE")
    private String resourceType;    
    
    @JsonProperty(value = "RESOURCE_URL")
    private String resourceUrl;

    @JsonProperty(value = "LINK_RESOURCE_ID")
    private String linkResourceId;
    
    @JsonProperty(value = "LINK_RESOURCE_SEQ")
    private int linkResourceSeq;
    
    @JsonProperty(value = "USE_YN")
    private String useYn;
    
    @JsonProperty(value = "USER_ID")
    private String userId;
    
    @JsonProperty(value = "TRANSACTION_TYPE")
    private String transactionType;

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

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getLinkResourceId() {
        return linkResourceId;
    }

    public void setLinkResourceId(String linkResourceId) {
        this.linkResourceId = linkResourceId;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getLinkResourceSeq() {
        return linkResourceSeq;
    }

    public void setLinkResourceSeq(int linkResourceSeq) {
        this.linkResourceSeq = linkResourceSeq;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "Resource [resourceLevel=" + resourceLevel + ", resourceId=" + resourceId + ", resourceNm=" + resourceNm
               + ", resourceType=" + resourceType + ", resourceUrl=" + resourceUrl + ", linkResourceId="
               + linkResourceId + ", linkResourceSeq=" + linkResourceSeq + ", useYn=" + useYn + ", userId=" + userId
               + ", transactionType=" + transactionType + "]";
    }
}
