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

import com.hyundaiuni.nxtims.domain.app.CodeDetail;
import com.hyundaiuni.nxtims.domain.app.CodeMaster;
import com.hyundaiuni.nxtims.util.WebUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodeControllerTest {
    private static final Log log = LogFactory.getLog(CodeControllerTest.class);

    private static final String URL = "/api/v1/app/code";

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
    public void testGetCodeDetailAll() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getCodeDetailAll")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$..CODE_DTL_CD").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }    

    @Test
    public void testGetCodeMasterListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();

            String query = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            mvc.perform(get(URL + "?inquiry=getCodeMasterListByParam&q=" + query + "&offset=0&limit=10")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$..CODE_MST_CD").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetCodeDetailListByCodeMstCd() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getCodeDetailListByCodeMstCd&codeMstCd=RESOURCE_TYPE")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                        jsonPath("$..CODE_DTL_CD").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetCodeMasterByCodeMstCd() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "/RESOURCE_TYPE")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$.CODE_MST_CD").value("RESOURCE_TYPE"));
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
            CodeMaster codeMaster = new CodeMaster();
            codeMaster.setCodeMstCd("TEST");
            codeMaster.setCodeMstNm("TEST");

            CodeDetail codeDetail1 = new CodeDetail();
            codeDetail1.setCodeMstCd(codeMaster.getCodeMstCd());
            codeDetail1.setCodeDtlCd("01");
            codeDetail1.setCodeDtlNm("01");

            CodeDetail codeDetail2 = new CodeDetail();
            codeDetail2.setCodeMstCd(codeMaster.getCodeMstCd());
            codeDetail2.setCodeDtlCd("02");
            codeDetail2.setCodeDtlNm("02");

            List<CodeDetail> codeDetaileList = new ArrayList<>();
            codeDetaileList.add(codeDetail1);
            codeDetaileList.add(codeDetail2);

            codeMaster.setCodeDetaileList(codeDetaileList);

            MvcResult result = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(JsonTestUtils.jsonStringFromObject(codeMaster))).andDo(
                    print()).andExpect(status().isOk()).andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                            jsonPath("$.CODE_MST_CD").value("TEST")).andReturn();

            log.info(result.getResponse().getContentAsString());

            String query = result.getResponse().getContentAsString();

            CodeMaster retrieveCodeMaster = JsonTestUtils.jsonStringToObject(query, CodeMaster.class);

            log.info(retrieveCodeMaster.getCodeMstCd());

            String codeMstCd = retrieveCodeMaster.getCodeMstCd();

            List<CodeDetail> retrieveCodeDetaileList = retrieveCodeMaster.getCodeDetaileList();

            if(CollectionUtils.isNotEmpty(retrieveCodeDetaileList)) {
                for(CodeDetail retrieveCodeDetail : retrieveCodeDetaileList) {
                    retrieveCodeDetail.setTransactionType("D");
                }
            }

            mvc.perform(put(URL + "/{codeMstCd}", codeMstCd).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                JsonTestUtils.jsonStringFromObject(retrieveCodeMaster))).andDo(print()).andExpect(status().isOk());

            mvc.perform(delete(URL + "/{codeMstCd}", codeMstCd)).andDo(print()).andExpect(status().isOk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
