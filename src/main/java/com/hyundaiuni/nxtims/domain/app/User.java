package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {
    private static final long serialVersionUID = 2053626949336009100L;

    @JsonProperty(value = "USER_ID")
    private String userId;

    @JsonProperty(value = "USER_NM")
    private String userNm;

    @JsonProperty(value = "USER_PWD")
    private String userPwd;

    @JsonProperty(value = "TEL_NO")
    private String telNo;

    @JsonProperty(value = "EMAIL")
    private String email;

    @JsonProperty(value = "USER_CL")
    private String userCl;

    @JsonProperty(value = "DEPT_CD")
    private String deptCd;

    @JsonProperty(value = "EMP_NO")
    private String empNo;

    @JsonProperty(value = "GRADE_CD")
    private String gradeCd;

    @JsonProperty(value = "CUST_CD")
    private String custCd;

    @JsonProperty(value = "APPROVE_GRADE")
    private String approveGrade;

    @JsonProperty(value = "APPROVE_SEQ")
    private String approveSeq;

    @JsonProperty(value = "APPROVE_TAG")
    private String approveTag;

    @JsonProperty(value = "EXPIRED_YN")
    private String expiredYn;

    @JsonProperty(value = "EXPIRED_YMD")
    private String expiredYmd;

    @JsonProperty(value = "LOCKED_YN")
    private String lockedYn;

    @JsonProperty(value = "PWD_EXPIRED_YN")
    private String pwdExpiredYn;

    @JsonProperty(value = "PWD_EXPIRED_YMD")
    private String pwdExpiredYmd;

    @JsonProperty(value = "USE_YN")
    private String useYn;

    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;

    @JsonProperty(value = "AUTH_LIST")
    private List<Auth> authList;

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

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserCl() {
        return userCl;
    }

    public void setUserCl(String userCl) {
        this.userCl = userCl;
    }

    public String getDeptCd() {
        return deptCd;
    }

    public void setDeptCd(String deptCd) {
        this.deptCd = deptCd;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getGradeCd() {
        return gradeCd;
    }

    public void setGradeCd(String gradeCd) {
        this.gradeCd = gradeCd;
    }

    public String getCustCd() {
        return custCd;
    }

    public void setCustCd(String custCd) {
        this.custCd = custCd;
    }

    public String getApproveGrade() {
        return approveGrade;
    }

    public void setApproveGrade(String approveGrade) {
        this.approveGrade = approveGrade;
    }

    public String getApproveSeq() {
        return approveSeq;
    }

    public void setApproveSeq(String approveSeq) {
        this.approveSeq = approveSeq;
    }

    public String getApproveTag() {
        return approveTag;
    }

    public void setApproveTag(String approveTag) {
        this.approveTag = approveTag;
    }

    public String getExpiredYn() {
        return expiredYn;
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

    public void setLockedYn(String lockedYn) {
        this.lockedYn = lockedYn;
    }

    public String getPwdExpiredYn() {
        return pwdExpiredYn;
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

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(String sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }
    
    public boolean isUse() {
        return "Y".equals(useYn) ? true : false;
    }
    
    public boolean isNonExpired() {
        return "Y".equals(expiredYn) ? false : true;
    }    
    
    public boolean isNonPwdExpired() {
        return "Y".equals(pwdExpiredYn) ? false : true;
    }
    
    public boolean isNonLocked() {
        return "Y".equals(lockedYn) ? false : true;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userNm=" + userNm + ", userPwd=" + userPwd + ", telNo=" + telNo
               + ", email=" + email + ", userCl=" + userCl + ", deptCd=" + deptCd + ", empNo=" + empNo + ", gradeCd="
               + gradeCd + ", custCd=" + custCd + ", approveGrade=" + approveGrade + ", approveSeq=" + approveSeq
               + ", approveTag=" + approveTag + ", expiredYn=" + expiredYn + ", expiredYmd=" + expiredYmd
               + ", lockedYn=" + lockedYn + ", pwdExpiredYn=" + pwdExpiredYn + ", pwdExpiredYmd=" + pwdExpiredYmd
               + ", useYn=" + useYn + ", sessionUserId=" + sessionUserId + ", authList=" + authList
               + "]";
    }
}
