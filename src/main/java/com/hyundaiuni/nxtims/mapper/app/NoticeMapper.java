package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.Notice;
import com.hyundaiuni.nxtims.domain.app.NoticeFile;

public interface NoticeMapper {
    public List<Notice> getNoticeListByParam(Map<String, Object> parameter);
    
    public Notice getNoticeByNoticeId(String noticeId);
    
    public List<NoticeFile> getNoticeFileListByNoticeId(String noticeId);
    
    public NoticeFile getNoticeFileByPk(Map<String, Object> parameter);
    
    public String getNoticeSequence();
    
    public void insertNotice(Notice notice);
    
    public int getNoticeFileSequence(String noticeId);
    
    public void insertNoticeFile(NoticeFile noticeFile);
    
    public void updateNotice(Notice notice);
    
    public void deleteNotice(String noticeId);
    
    public void deleteNoticeFileByPk(Map<String, Object> parameter);
    
    public void deleteNoticeFileByNoticeId(String noticeId);
}
