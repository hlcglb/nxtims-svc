package com.hyundaiuni.nxtims.controller.sample;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
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
public class SampleControllerTests {
    private static final Log log = LogFactory.getLog(SampleControllerTests.class);

    private static final String URL = "/api/v1/sample";

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
    public void testGetOk() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "/test")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.USER_ID").value("test"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetError() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "/testx")).andDo(print()).andExpect(status().is4xxClientError()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$.CODE").value("MSG.USER_NOT_FOUND"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testInsert() {
        Exception ex = null;

        try {
            Map<String, Object> map = new HashMap<>();
            map.put("REMARK", "SampleControllerTests - " + new Date().toString());

            mvc.perform(post(URL, map).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                JSONObject.toJSONString(map))).andDo(print()).andExpect(status().isOk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
