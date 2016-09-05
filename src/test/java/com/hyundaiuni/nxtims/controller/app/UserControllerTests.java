package com.hyundaiuni.nxtims.controller.app;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
public class UserControllerTests {
    private static final String URL = "/api/v1/app/users";

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        this.mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testGetUser() throws Exception {
        mvc.perform(get(URL + "/test")).andDo(print()).andExpect(status().isOk()).andExpect(
            content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.USER_ID").value("test"));
    }

    @Test
    public void testGetMenuByUserId() throws Exception {
        mvc.perform(get(URL + "/menus/test")).andDo(print()).andExpect(status().isOk()).andExpect(
            content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$..RESOURCE_ID").isArray());
    }

    @Test
    public void testOnAuthenticationSuccess() throws Exception {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("USER_ID", "test");
        parameter.put("SESSION_ID", "DB18EBE12C90845710D544C7A15D7072");
        parameter.put("ACCESS_IP", "1.1.1.1");

        mvc.perform(
            post(URL + "/onAuthenticationSuccess", parameter).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                JSONObject.toJSONString(parameter))).andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void testOnAuthenticationFailure() throws Exception {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("USER_ID", "test");

        mvc.perform(post(URL + "/onAuthenticationFailure", parameter).contentType(MediaType.APPLICATION_JSON_UTF8).content(
            JSONObject.toJSONString(parameter))).andDo(print()).andExpect(status().isOk());
    }
    
    public void testOnLogout() throws Exception {
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("USER_ID", "test");
        parameter.put("SESSION_ID", "DB18EBE12C90845710D544C7A15D7072");

        mvc.perform(
            post(URL + "/onLogout", parameter).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                JSONObject.toJSONString(parameter))).andDo(print()).andExpect(status().isOk());
    }    
}
