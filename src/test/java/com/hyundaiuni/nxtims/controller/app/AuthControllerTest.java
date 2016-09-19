package com.hyundaiuni.nxtims.controller.app;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.Auth;
import com.hyundaiuni.nxtims.domain.app.AuthResource;
import com.hyundaiuni.nxtims.util.WebUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthControllerTest {
    private static final Log log = LogFactory.getLog(AuthControllerTest.class);

    private static final String URL = "/api/v1/app/auth";

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
    public void testGetAuthListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("authId", "ADMIN");

            String query = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            mvc.perform(get(URL + "?inquiry=getAuthListByParam&q=" + query + "&offset=0&limit=10")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$..AUTH_ID").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetAuthResourceListByAuthId() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getAuthResourceListByAuthId&authId=ADMIN")).andDo(print()).andExpect(
                status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$..AUTH_ID").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetAuthByAuthId() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "/ADMIN")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$.AUTH_ID").value("ADMIN"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetNotExistsAuthResourceListByAuthId() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getNotExistsAuthResourceListByAuthId&authId=ADMIN")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$..RESOURCE_ID").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testSaveAuth() {
        Exception ex = null;

        try {
            Auth auth = new Auth();

            auth.setAuthId("TEST");
            auth.setAuthNm("TEST");
            auth.setUserId("200065");

            AuthResource authResource1 = new AuthResource();

            authResource1.setAuthId(auth.getAuthId());
            authResource1.setResourceId("000000");

            AuthResource authResource2 = new AuthResource();

            authResource2.setAuthId(auth.getAuthId());
            authResource2.setResourceId("000001");

            List<AuthResource> authResourceList = new ArrayList<>();
            authResourceList.add(authResource1);
            authResourceList.add(authResource2);

            auth.setAuthResourceList(authResourceList);

            MvcResult result = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringFromObject(auth))).andDo(
                    print()).andExpect(status().isOk()).andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                            jsonPath("$.AUTH_ID").value("TEST")).andReturn();

            log.info(result.getResponse().getContentAsString());

            String query = result.getResponse().getContentAsString();

            Auth retrieveAuth = jsonStringToObject(query, Auth.class);

            log.info(retrieveAuth.getAuthId());

            String authId = retrieveAuth.getAuthId();

            List<AuthResource> retrieveAuthResourceList = retrieveAuth.getAuthResourceList();

            if(CollectionUtils.isNotEmpty(retrieveAuthResourceList)) {
                for(AuthResource retrieveAuthResource : retrieveAuthResourceList) {
                    retrieveAuthResource.setTransactionType("D");
                }
            }

            mvc.perform(put(URL + "/{authId}", authId).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                jsonStringFromObject(retrieveAuth))).andDo(print()).andExpect(status().isOk());

            mvc.perform(delete(URL + "/{authId}", authId)).andDo(print()).andExpect(status().isOk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    private String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private <T> T jsonStringToObject(String result, Class<T> valueType) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(result, valueType);
    }
}
