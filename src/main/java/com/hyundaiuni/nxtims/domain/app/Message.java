package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Serializable {
    private static final long serialVersionUID = 7135269501094652136L;
    
    @JsonProperty(value = "MSG_GRP_CD")
    private String msgGrpCd;
    
    @JsonProperty(value = "MSG_CD")
    private String msgCd;
    
    @JsonProperty(value = "LANG_CD")
    private String langCd;    
        
    @JsonProperty(value = "MSG_NM")
    private String msgNm;

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

    @Override
    public String toString() {
        return "Message [msgGrpCd=" + msgGrpCd + ", msgCd=" + msgCd + ", langCd=" + langCd + ", msgNm=" + msgNm + "]";
    }
}
