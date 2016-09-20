package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CodeDetail implements Serializable {
    private static final long serialVersionUID = -1848974255135085179L;

    @JsonProperty(value = "CODE_MST_CD")
    private String codeMstCd;

    @JsonProperty(value = "CODE_DTL_CD")
    private String codeDtlCd;

    @JsonProperty(value = "CODE_DTL_NM")
    private String codeDtlNm;

    @JsonProperty(value = "MSG_GRP_CD")
    private String msgGrpCd;

    @JsonProperty(value = "MSG_CD")
    private String msgCd;

    @JsonProperty(value = "SORT_SEQ")
    private int sortSeq;

    @JsonProperty(value = "USE_YN")
    private String useYn;

    @JsonProperty(value = "REF_CD1")
    private String refCd1;

    @JsonProperty(value = "REF_NM1")
    private String refNm1;

    @JsonProperty(value = "REF_CD2")
    private String refCd2;

    @JsonProperty(value = "REF_NM2")
    private String refNm2;

    @JsonProperty(value = "REF_CD3")
    private String refCd3;

    @JsonProperty(value = "REF_NM3")
    private String refNm3;

    @JsonProperty(value = "REF_CD4")
    private String refCd4;

    @JsonProperty(value = "REF_NM4")
    private String refNm4;

    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;

    @JsonProperty(value = "TRANSACTION_TYPE")
    private String transactionType;

    public String getCodeMstCd() {
        return codeMstCd;
    }

    public void setCodeMstCd(String codeMstCd) {
        this.codeMstCd = codeMstCd;
    }

    public String getCodeDtlCd() {
        return codeDtlCd;
    }

    public void setCodeDtlCd(String codeDtlCd) {
        this.codeDtlCd = codeDtlCd;
    }

    public String getCodeDtlNm() {
        return codeDtlNm;
    }

    public void setCodeDtlNm(String codeDtlNm) {
        this.codeDtlNm = codeDtlNm;
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

    public int getSortSeq() {
        return sortSeq;
    }

    public void setSortSeq(int sortSeq) {
        this.sortSeq = sortSeq;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getRefCd1() {
        return refCd1;
    }

    public void setRefCd1(String refCd1) {
        this.refCd1 = refCd1;
    }

    public String getRefNm1() {
        return refNm1;
    }

    public void setRefNm1(String refNm1) {
        this.refNm1 = refNm1;
    }

    public String getRefCd2() {
        return refCd2;
    }

    public void setRefCd2(String refCd2) {
        this.refCd2 = refCd2;
    }

    public String getRefNm2() {
        return refNm2;
    }

    public void setRefNm2(String refNm2) {
        this.refNm2 = refNm2;
    }

    public String getRefCd3() {
        return refCd3;
    }

    public void setRefCd3(String refCd3) {
        this.refCd3 = refCd3;
    }

    public String getRefNm3() {
        return refNm3;
    }

    public void setRefNm3(String refNm3) {
        this.refNm3 = refNm3;
    }

    public String getRefCd4() {
        return refCd4;
    }

    public void setRefCd4(String refCd4) {
        this.refCd4 = refCd4;
    }

    public String getRefNm4() {
        return refNm4;
    }

    public void setRefNm4(String refNm4) {
        this.refNm4 = refNm4;
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
        return "CodeDetail [codeMstCd=" + codeMstCd + ", codeDtlCd=" + codeDtlCd + ", codeDtlNm=" + codeDtlNm
               + ", msgGrpCd=" + msgGrpCd + ", msgCd=" + msgCd + ", sortSeq=" + sortSeq + ", useYn=" + useYn
               + ", refCd1=" + refCd1 + ", refNm1=" + refNm1 + ", refCd2=" + refCd2 + ", refNm2=" + refNm2 + ", refCd3="
               + refCd3 + ", refNm3=" + refNm3 + ", refCd4=" + refCd4 + ", refNm4=" + refNm4 + ", sessionUserId=" + sessionUserId
               + ", transactionType=" + transactionType + "]";
    }
}
