package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Auth implements Serializable {
    private static final long serialVersionUID = -5088984642210923982L;
    
    @JsonProperty(value = "AUTH_ID")
    private String authId;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }    
}
