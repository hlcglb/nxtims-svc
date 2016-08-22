package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth implements Serializable {
    private static final long serialVersionUID = -5088984642210923982L;
    
    @JsonProperty(value = "AUTH_ID")
    private String authId;
    
    @JsonProperty(value = "AUTH_NM")
    private String authNm;

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
}
