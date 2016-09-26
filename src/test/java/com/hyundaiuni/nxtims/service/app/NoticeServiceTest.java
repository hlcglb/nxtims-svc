package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundaiuni.nxtims.domain.app.Notice;
import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeServiceTest {
    private static final Log log = LogFactory.getLog(NoticeServiceTest.class);

    @Autowired
    private NoticeService noticeService;

    @Test
    public void testGetNoticeListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();

            noticeService.getNoticeListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetNotice() {
        Exception ex = null;

        try {
            noticeService.getNotice("0000000000");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);

        try {
            noticeService.getNotice("0000000001");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("not found");
        }
    }

    @Test
    public void testInsertNotice() {
        Exception ex = null;

        try {
            Notice notice = new Notice();
            notice.setTitle("TEST");
            notice.setContent("TEST");
            notice.setOpenYmd("20160923");
            notice.setCloseYmd("20160923");

            Notice tempNotice = noticeService.insertNotice(notice);

            noticeService.deleteNotice(tempNotice.getNoticeId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testUpdateNotice() {
        Exception ex = null;

        try {
            Notice notice = new Notice();
            notice.setTitle("TEST");
            notice.setContent("TEST");
            notice.setOpenYmd("20160923");
            notice.setCloseYmd("20160923");

            Notice tempNotice = noticeService.insertNotice(notice);
            tempNotice.setTitle("TEST1");

            noticeService.updateNotice(tempNotice);
            noticeService.deleteNotice(tempNotice.getNoticeId());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
