package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeMaster implements Serializable {
    private static final long serialVersionUID = 53134511192466102L;
    
    @JsonProperty(value = "CODE_MST_CD")
    private String codeMstCd;
    
    @JsonProperty(value = "CODE_MST_NM")
    private String codeMstNm;
    
    @JsonProperty(value = "USER_ID")
    private String userId;
    
    @JsonProperty(value = "CODE_DETAIL_LIST")
    private List<CodeDetail> codeDetaileList;

    public String getCodeMstCd() {
        return codeMstCd;
    }

    public void setCodeMstCd(String codeMstCd) {
        this.codeMstCd = codeMstCd;
    }

    public String getCodeMstNm() {
        return codeMstNm;
    }

    public void setCodeMstNm(String codeMstNm) {
        this.codeMstNm = codeMstNm;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CodeDetail> getCodeDetaileList() {
        return codeDetaileList;
    }

    public void setCodeDetaileList(List<CodeDetail> codeDetaileList) {
        this.codeDetaileList = codeDetaileList;
    }

    @Override
    public String toString() {
        return "CodeMaster [codeMstCd=" + codeMstCd + ", codeMstNm=" + codeMstNm + ", userId=" + userId
               + ", codeDetaileList=" + codeDetaileList + "]";
    }
}
