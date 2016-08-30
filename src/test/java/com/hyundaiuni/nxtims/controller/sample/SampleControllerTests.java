package com.hyundaiuni.nxtims.controller.sample;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private static final String URL = "/api/v1/sample";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGet() throws Exception {
        mvc.perform(get(URL + "/test")).andDo(print()).andExpect(status().isOk()).andExpect(
            content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.USER_ID").value("test"));
    }

    @Test
    public void testInsert() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("REMARK", "SampleControllerTests - " + new Date().toString());

        mvc.perform(
            post(URL, map).contentType(MediaType.APPLICATION_JSON_UTF8).content(JSONObject.toJSONString(map))).andDo(
                print()).andExpect(status().isOk());
    }
}
