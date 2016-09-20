package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth implements Serializable {
    private static final long serialVersionUID = -5088984642210923982L;
    
    @JsonProperty(value = "AUTH_ID")
    private String authId;
    
    @JsonProperty(value = "AUTH_NM")
    private String authNm;
    
    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;
    
    @JsonProperty(value = "AUTH_RESOURCE_LIST")
    private List<AuthResource> authResourceList;

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

    public String getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(String sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    public List<AuthResource> getAuthResourceList() {
        return authResourceList;
    }

    public void setAuthResourceList(List<AuthResource> authResourceList) {
        this.authResourceList = authResourceList;
    }

    @Override
    public String toString() {
        return "Auth [authId=" + authId + ", authNm=" + authNm + ", sessionUserId=" + sessionUserId
               + ", authResourceList=" + authResourceList + "]";
    }       
}
