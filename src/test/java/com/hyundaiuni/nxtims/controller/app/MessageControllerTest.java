package com.hyundaiuni.nxtims.controller.app;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageControllerTest {
    private static final Log log = LogFactory.getLog(MessageControllerTest.class);

    private static final String URL = "/api/v1/app/messages";

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
    public void testGetMessagesByLanguageCodeIsOk() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getMessagesByLanguageCode&languageCode=ko_KR")).andDo(print()).andExpect(
                status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$..MSG_CD").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetMessagesByLanguageCodeIsError() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getMessagesByLanguageCode&languageCode=en_US")).andDo(print()).andExpect(
                status().is4xxClientError()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$.CODE").value("MSG.LOCALE_NOT_SUPPORTED"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
