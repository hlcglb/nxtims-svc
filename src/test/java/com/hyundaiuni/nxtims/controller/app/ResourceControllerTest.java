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
import com.hyundaiuni.nxtims.domain.app.Resource;
import com.hyundaiuni.nxtims.util.WebUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResourceControllerTest {
    private static final Log log = LogFactory.getLog(ResourceControllerTest.class);

    private static final String URL = "/api/v1/app/resources";

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
    public void testGetResources() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getResourceAuthAll")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$..AUTH_ID").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetResourceListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("resourceNm", "컨테이너운송");

            String query = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            mvc.perform(get(URL + "?inquiry=getResourceListByParam&q=" + query + "&offset=0&limit=10")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$..RESOURCE_NM").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetResourceById() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "/000000")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$.RESOURCE_NM").value("컨테이너운송"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testCUDResource() {
        Exception ex = null;

        try {
            Resource resource = new Resource();

            resource.setResourceNm("TEST");
            resource.setResourceType("03");
            resource.setResourceUrl("---");
            resource.setUseYn("Y");

            MvcResult result = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringFromObject(resource))).andDo(
                    print()).andExpect(status().isOk()).andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                            jsonPath("$.RESOURCE_NM").value("TEST")).andReturn();

            log.info(result.getResponse().getContentAsString());

            String query = result.getResponse().getContentAsString();

            Resource retrieveResource = jsonStringToObject(query, Resource.class);

            log.info(retrieveResource.getLinkResourceId());

            String resourceId = retrieveResource.getResourceId();

            resource.setResourceNm("TEST1");

            mvc.perform(put(URL + "/{resourceId}", resourceId).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                jsonStringFromObject(retrieveResource))).andDo(print()).andExpect(status().isOk());

            mvc.perform(delete(URL + "/{resourceId}", resourceId)).andDo(print()).andExpect(status().isOk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testSaveResource() {
        Exception ex = null;

        try {
            List<Resource> resourceList = new ArrayList<>();

            Resource updateResource = new Resource();

            updateResource.setResourceId("000000");
            updateResource.setResourceNm("컨테이너운송");
            updateResource.setResourceType("01");
            updateResource.setResourceUrl("");
            updateResource.setUseYn("Y");
            updateResource.setTransactionType("U");

            resourceList.add(updateResource);

            mvc.perform(post(URL + "/save").contentType(MediaType.APPLICATION_JSON_UTF8).content(
                jsonStringFromObject(resourceList))).andDo(print()).andExpect(status().isOk());
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
