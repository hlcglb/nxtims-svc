package com.hyundaiuni.nxtims.controller.app;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hyundaiuni.nxtims.domain.app.Notice;
import com.hyundaiuni.nxtims.domain.app.NoticeFile;
import com.hyundaiuni.nxtims.util.WebUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoticeControllerTest {
    private static final Log log = LogFactory.getLog(NoticeControllerTest.class);

    private static final String URL = "/api/v1/app/notice";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() {
        Exception ex = null;

        try {
            this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetNoticeListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();

            String query = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            mvc.perform(get(URL + "?inquiry=getNoticeListByParam&q=" + query + "&offset=0&limit=10")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$..NOTICE_ID").isArray());
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
            mvc.perform(get(URL + "/0000000002")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$.NOTICE_ID").value("0000000002"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testSaveCode() {
        Exception ex = null;

        try {
            Notice notice = new Notice();
            notice.setTitle("TEST");
            notice.setContent("TEST");
            notice.setOpenYmd("20160923");
            notice.setCloseYmd("20160923");

            NoticeFile noticeFile = new NoticeFile();
            noticeFile.setFileNm("TEXT.txt");
            noticeFile.setFileUrl("/app/arc/TEXT.txt");

            List<NoticeFile> noticeFileList = new ArrayList<>();
            noticeFileList.add(noticeFile);

            notice.setNoticeFileList(noticeFileList);

            MvcResult result = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonTestUtils.jsonStringFromObject(notice))).andDo(
                    print()).andExpect(status().isOk()).andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                            jsonPath("$.TITLE").value("TEST")).andReturn();

            String query = result.getResponse().getContentAsString();

            Notice retrieveNotice = JsonTestUtils.jsonStringToObject(query, Notice.class);

            String noticeId = retrieveNotice.getNoticeId();

            List<NoticeFile> retrieveNoticeFileList = retrieveNotice.getNoticeFileList();

            if(CollectionUtils.isNotEmpty(retrieveNoticeFileList)) {
                for(NoticeFile tempNoticeFile : retrieveNoticeFileList) {
                    tempNoticeFile.setTransactionType("D");
                }
            }

            mvc.perform(post(URL + "/{noticeId}", noticeId).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                JsonTestUtils.jsonStringFromObject(retrieveNotice))).andDo(print()).andExpect(status().isOk());

            mvc.perform(delete(URL + "/{noticeId}", noticeId)).andDo(print()).andExpect(status().isOk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
