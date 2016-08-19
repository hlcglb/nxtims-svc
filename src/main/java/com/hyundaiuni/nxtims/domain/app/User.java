package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialVersionUID = 2053626949336009100L;

    @JsonProperty(value = "USER_ID")
    private String userId = null;

    @JsonProperty(value = "USER_NM")
    private String userNm = null;

    @JsonProperty(value = "USER_PWD")
    private String userPwd = null;

    @JsonProperty(value = "EXPIRED_YN")
    private String expiredYn = null;

    @JsonProperty(value = "EXPIRED_YMD")
    private String expiredYmd = null;

    @JsonProperty(value = "LOCKED_YN")
    private String lockedYn = null;

    @JsonProperty(value = "PWD_EXPIRED_YN")
    private String pwdExpiredYn = null;

    @JsonProperty(value = "PWD_EXPIRED_YMD")
    private String pwdExpiredYmd = null;

    @JsonProperty(value = "USE_YN")
    private String useYn = null;

    @JsonProperty(value = "REG_USER_ID")
    private String regUserId = null;

    @JsonProperty(value = "UPD_USER_ID")
    private String updUserId = null;

    @JsonProperty(value = "AUTH_LIST")
    private List<Auth> authList = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getExpiredYn() {
        return expiredYn;
    }

    public boolean isNonExpired() {
        return "Y".equals(expiredYn) ? false : true;
    }

    public void setExpiredYn(String expiredYn) {
        this.expiredYn = expiredYn;
    }

    public String getExpiredYmd() {
        return expiredYmd;
    }

    public void setExpiredYmd(String expiredYmd) {
        this.expiredYmd = expiredYmd;
    }

    public String getLockedYn() {
        return lockedYn;
    }
    
    public boolean isNonLocked() {
        return "Y".equals(lockedYn) ? false : true;
    }    

    public void setLockedYn(String lockedYn) {
        this.lockedYn = lockedYn;
    }

    public String getPwdExpiredYn() {
        return pwdExpiredYn;
    }
    
    public boolean isNonPwdExpired() {
        return "Y".equals(pwdExpiredYn) ? false : true;
    }    

    public void setPwdExpiredYn(String pwdExpiredYn) {
        this.pwdExpiredYn = pwdExpiredYn;
    }

    public String getPwdExpiredYmd() {
        return pwdExpiredYmd;
    }

    public void setPwdExpiredYmd(String pwdExpiredYmd) {
        this.pwdExpiredYmd = pwdExpiredYmd;
    }

    public String getUseYn() {
        return useYn;
    }
    
    public boolean isUse() {
        return "Y".equals(useYn) ? true : false;
    }     

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRegUserId() {
        return regUserId;
    }

    public void setRegUserId(String regUserId) {
        this.regUserId = regUserId;
    }

    public String getUpdUserId() {
        return updUserId;
    }

    public void setUpdUserId(String updUserId) {
        this.updUserId = updUserId;
    }

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }
}
