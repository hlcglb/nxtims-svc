package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.Notice;
import com.hyundaiuni.nxtims.domain.app.NoticeFile;

public class NoticeTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            Notice notice = new Notice();
            notice.setNoticeId("12345");
            notice.setTitle("TEST");
            notice.setContent("TEST");
            notice.setOpenYmd("20160923");
            notice.setCloseYmd("20160923");

            NoticeFile noticeFile = new NoticeFile();
            noticeFile.setNoticeId(notice.getNoticeId());
            noticeFile.setSeq(1);
            noticeFile.setFileNm("TEXT.txt");
            noticeFile.setFileUrl("/app/arc/TEXT.txt");

            List<NoticeFile> noticeFileList = new ArrayList<>();
            noticeFileList.add(noticeFile);

            notice.setNoticeFileList(noticeFileList);

            mapper.writeValueAsString(notice);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testJsonToObject() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            String json = "{\"NOTICE_ID\":\"12345\",\"TITLE\":\"TEST\",\"CONTENT\":\"TEST\",\"OPEN_YMD\":\"20160923\",\"CLOSE_YMD\":\"20160923\",\"NOTICE_FILE_LIST\":[{\"NOTICE_ID\":\"12345\",\"SEQ\":1,\"FILE_NM\":\"TEXT.txt\",\"FILE_URL\":\"/app/arc/TEXT.txt\",\"SESSION_USER_ID\":null,\"TRANSACTION_TYPE\":null}],\"SESSION_USER_ID\":null}";

            mapper.readValue(json, Notice.class);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }
}
