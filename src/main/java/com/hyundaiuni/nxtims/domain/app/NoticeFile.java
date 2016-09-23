package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NoticeFile implements Serializable {
    private static final long serialVersionUID = -7252512647801392453L;

    @JsonProperty(value = "NOTICE_ID")
    private String noticeId;

    @JsonProperty(value = "SEQ")
    private int seq;

    @JsonProperty(value = "FILE_NM")
    private String fileNm;

    @JsonProperty(value = "FILE_URL")
    private String fileUrl;

    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;

    @JsonProperty(value = "TRANSACTION_TYPE")
    private String transactionType;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
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
        return "NoticeFile [noticeId=" + noticeId + ", seq=" + seq + ", fileNm=" + fileNm + ", fileUrl=" + fileUrl
               + ", sessionUserId=" + sessionUserId + ", transactionType=" + transactionType + "]";
    }
}
