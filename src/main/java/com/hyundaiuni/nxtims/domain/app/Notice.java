package com.hyundaiuni.nxtims.domain.app;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Notice implements Serializable {
    private static final long serialVersionUID = 7004432780658766901L;

    @JsonProperty(value = "NOTICE_ID")
    private String noticeId;

    @JsonProperty(value = "TITLE")
    private String title;
    
    @JsonProperty(value = "CONTENT")
    private String content;
    
    @JsonProperty(value = "OPEN_YMD")
    private String openYmd;
    
    @JsonProperty(value = "CLOSE_YMD")
    private String closeYmd;

    @JsonProperty(value = "NOTICE_FILE_LIST")
    private List<NoticeFile> noticeFileList;
    
    @JsonProperty(value = "SESSION_USER_ID")
    private String sessionUserId;

    public String getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(String noticeId) {
        this.noticeId = noticeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOpenYmd() {
        return openYmd;
    }

    public void setOpenYmd(String openYmd) {
        this.openYmd = openYmd;
    }

    public String getCloseYmd() {
        return closeYmd;
    }

    public void setCloseYmd(String closeYmd) {
        this.closeYmd = closeYmd;
    }

    public List<NoticeFile> getNoticeFileList() {
        return noticeFileList;
    }

    public void setNoticeFileList(List<NoticeFile> noticeFileList) {
        this.noticeFileList = noticeFileList;
    }

    public String getSessionUserId() {
        return sessionUserId;
    }

    public void setSessionUserId(String sessionUserId) {
        this.sessionUserId = sessionUserId;
    }

    @Override
    public String toString() {
        return "Notice [noticeId=" + noticeId + ", title=" + title + ", content=" + content + ", openYmd=" + openYmd
               + ", closeYmd=" + closeYmd + ", noticeFileList=" + noticeFileList + ", sessionUserId=" + sessionUserId
               + "]";
    }
}