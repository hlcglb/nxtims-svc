package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {
    private static final long serialVersionUID = 7135269501094652136L;
    
    @JsonProperty(value = "MSG_PK")
    private String msgPk;
    
    @JsonProperty(value = "MSG_GRP_CD")
    private String msgGrpCd;
    
    @JsonProperty(value = "MSG_CD")
    private String msgCd;
        
    @JsonProperty(value = "MSG_NM")
    private String msgNm;
    
    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;

    @JsonProperty(value = "MSG_LOC_LIST")
    private List<MessageLocale> msgLocList;        

    public String getMsgPk() {
        return msgPk;
    }

    public void setMsgPk(String msgPk) {
        this.msgPk = msgPk;
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

    public List<MessageLocale> getMsgLocList() {
        return msgLocList;
    }

    public void setMsgLocList(List<MessageLocale> msgLocList) {
        this.msgLocList = msgLocList;
    }

    @Override
    public String toString() {
        return "Message [msgPk=" + msgPk + ", msgGrpCd=" + msgGrpCd + ", msgCd=" + msgCd + ", msgNm=" + msgNm
               + ", sessionUserId=" + sessionUserId + ", msgLocList=" + msgLocList + "]";
    }
}
