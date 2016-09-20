package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageLocale implements Serializable {
    private static final long serialVersionUID = 7135269501094652136L;
    
    @JsonProperty(value = "MSG_LOC_PK")
    private String msgLocPk;
    
    @JsonProperty(value = "MSG_GRP_CD")
    private String msgGrpCd;
    
    @JsonProperty(value = "MSG_CD")
    private String msgCd;
    
    @JsonProperty(value = "LANG_CD")
    private String langCd;    
        
    @JsonProperty(value = "MSG_NM")
    private String msgNm;
    
    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;
    
    @JsonProperty(value = "TRANSACTION_TYPE")
    private String transactionType;      
    
    public String getMsgLocPk() {
        return msgLocPk;
    }

    public void setMsgLocPk(String msgLocPk) {
        this.msgLocPk = msgLocPk;
    }

    public String getMsgGrpCd() {
        return msgGrpCd;
    }

    public void setMsgGrpCd(String msgGrpCd) {
        this.msgGrpCd = msgGrpCd;
    }

    public String getMsgCd() {
        return msgCd;
    }

    public void setMsgCd(String msgCd) {
        this.msgCd = msgCd;
    }

    public String getLangCd() {
        return langCd;
    }

    public void setLangCd(String langCd) {
        this.langCd = langCd;
    }

    public String getMsgNm() {
        return msgNm;
    }

    public void setMsgNm(String msgNm) {
        this.msgNm = msgNm;
    }
    
    public String getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(String sessionUserId) {
        this.sessionUserId = sessionUserId;
    }   

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "MessageLocale [msgLocPk=" + msgLocPk + ", msgGrpCd=" + msgGrpCd + ", msgCd=" + msgCd + ", langCd="
               + langCd + ", msgNm=" + msgNm + ", sessionUserId=" + sessionUserId + ", transactionType=" + transactionType + "]";
    }
}
