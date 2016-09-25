package com.hyundaiuni.nxtims.service.app;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hyundaiuni.nxtims.domain.app.Notice;
import com.hyundaiuni.nxtims.domain.app.NoticeFile;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.NoticeMapper;

@Service
public class NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Transactional(readOnly = true)
    public List<Notice> getNoticeListByParam(Map<String, Object> parameter, int offset, int limit) {
        Assert.notNull(parameter, "parameter must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

        parameter.put("offset", offset);
        parameter.put("limit", limit);

        return noticeMapper.getNoticeListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public Notice getNotice(String noticeId) {
        Assert.notNull(noticeId, "noticeId must not be null");

        Notice notice = noticeMapper.getNoticeByNoticeId(noticeId);

        if(notice == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", noticeId + " not found.", null);
        }

        notice.setNoticeFileList(noticeMapper.getNoticeFileListByNoticeId(noticeId));

        return notice;
    }

    @Transactional
    public Notice insertNotice(Notice notice) {
        Assert.notNull(notice, "notice must not be null");

        checkMandatoryNotice(notice);

        notice.setNoticeId(noticeMapper.getNoticeSequence());

        noticeMapper.insertNotice(notice);

        List<NoticeFile> noticeFileList = notice.getNoticeFileList();

        if(CollectionUtils.isNotEmpty(noticeFileList)) {
            for(NoticeFile noticeFile : noticeFileList) {
                noticeFile.setNoticeId(notice.getNoticeId());

                checkMandatoryNoticeFile(noticeFile);

                noticeFile.setSeq(noticeMapper.getNoticeFileSequence(noticeFile.getNoticeId()));

                noticeMapper.insertNoticeFile(noticeFile);
            }
        }

        return getNotice(notice.getNoticeId());
    }

    @Transactional
    public void updateNotice(Notice notice) {
        Assert.notNull(notice, "notice must not be null");

        if(StringUtils.isEmpty(notice.getNoticeId())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "NOTICE_ID must not be null.", new String[] {"NOTICE_ID"});
        }

        checkMandatoryNotice(notice);

        if(noticeMapper.getNoticeByNoticeId(notice.getNoticeId()) == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", notice.getNoticeId() + " not found.", null);
        }

        noticeMapper.updateNotice(notice);

        List<NoticeFile> noticeFileList = notice.getNoticeFileList();

        if(CollectionUtils.isNotEmpty(noticeFileList)) {
            for(NoticeFile noticeFile : noticeFileList) {
                String noticeFileTransactionType = noticeFile.getTransactionType();

                noticeFile.setNoticeId(notice.getNoticeId());

                if("C".equals(noticeFileTransactionType)) {
                    checkMandatoryNoticeFile(noticeFile);

                    noticeFile.setSeq(noticeMapper.getNoticeFileSequence(noticeFile.getNoticeId()));

                    noticeMapper.insertNoticeFile(noticeFile);
                }
                else if("D".equals(noticeFileTransactionType)) {
                    Map<String, Object> parameter = new HashMap<>();
                    parameter.put("noticeId", noticeFile.getNoticeId());
                    parameter.put("seq", noticeFile.getSeq());

                    if(noticeMapper.getNoticeFileByPk(parameter) == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }

                    noticeMapper.deleteNoticeFileByPk(parameter);
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        noticeFileTransactionType + " was not supported.", null);
                }
            }
        }
    }

    @Transactional
    public void deleteNotice(String noticeId) {
        Assert.notNull(noticeId, "noticeId must not be null");
        
        Notice notice = noticeMapper.getNoticeByNoticeId(noticeId);

        if(notice == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", noticeId + " not found.", null);
        }
        
        List<NoticeFile> noticeFileList = notice.getNoticeFileList();

        if(CollectionUtils.isNotEmpty(noticeFileList)) {
            for(NoticeFile noticeFile : noticeFileList) {
                FileUtils.deleteQuietly(new File(noticeFile.getFileUrl()));
            }
        }        

        noticeMapper.deleteNoticeFileByNoticeId(noticeId);
        noticeMapper.deleteNotice(noticeId);
    }

    private void checkMandatoryNotice(Notice notice) {
        if(StringUtils.isEmpty(notice.getTitle())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "TITLE must not be null.", new String[] {"TITLE"});
        }

        if(StringUtils.isEmpty(notice.getContent())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CONTENT must not be null.", new String[] {"CONTENT"});
        }

        if(StringUtils.isEmpty(notice.getOpenYmd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "OPEN_YMD must not be null.", new String[] {"OPEN_YMD"});
        }

        if(StringUtils.isEmpty(notice.getCloseYmd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "CLOSE_YMD must not be null.", new String[] {"CLOSE_YMD"});
        }
    }

    private void checkMandatoryNoticeFile(NoticeFile noticeFile) {
        if(StringUtils.isEmpty(noticeFile.getFileNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "FILE_NM must not be null.", new String[] {"FILE_NM"});
        }
    }
}
