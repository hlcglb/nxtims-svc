package com.hyundaiuni.nxtims.controller.app;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import com.hyundaiuni.nxtims.domain.app.Message;
import com.hyundaiuni.nxtims.domain.app.MessageLocale;
import com.hyundaiuni.nxtims.util.WebUtils;

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
    public void testGetMessageListByLanguageCodeIsOk() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getMessageListByLanguageCode&languageCode=ko_KR")).andDo(print()).andExpect(
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
    public void testGetMessageListByLanguageCodeIsError() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getMessageListByLanguageCode&languageCode=en_US")).andDo(print()).andExpect(
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

    @Test
    public void testGetMessageListByParam() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("msgGrpCd", "MSG");

            String query = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            mvc.perform(get(URL + "?inquiry=getMessageListByParam&q=" + query + "&offset=0&limit=10")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$..MSG_PK").isArray());

            parameter = new HashMap<>();

            String msgCd = "NO_DATA_FOUND^TRANSACTION_TYPE_NOT_SUPPORTED";

            parameter.put("msgCd", msgCd);

            query = WebUtils.mapToRequestParam(parameter, ',', '=', "UTF-8");

            mvc.perform(get(URL + "?inquiry=getMessageListByParam&q=" + query + "&offset=0&limit=10")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$..MSG_PK").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetMessageLocaleListByParam() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "?inquiry=getMessageLocaleListByParam&msgGrpCd=MSG&msgCd=NO_DATA_FOUND")).andDo(
                print()).andExpect(status().isOk()).andExpect(
                    content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(jsonPath("$..MSG_CD").isArray());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetMessageByMsgPk() {
        Exception ex = null;

        try {
            mvc.perform(get(URL + "/0000000013")).andDo(print()).andExpect(status().isOk()).andExpect(
                content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                    jsonPath("$.MSG_CD").value("USER_NOT_FOUND"));
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testSaveMessage() {
        Exception ex = null;

        try {
            Message message = new Message();

            message.setMsgGrpCd("MSG");
            message.setMsgCd("JUNIT");
            message.setMsgNm("JUNIT");
            message.setUserId("TEST");

            MessageLocale messageLocaleKo = new MessageLocale();
            messageLocaleKo.setMsgGrpCd(message.getMsgGrpCd());
            messageLocaleKo.setMsgCd(message.getMsgCd());
            messageLocaleKo.setMsgNm("JUNIT");
            messageLocaleKo.setLangCd("ko_KR");
            messageLocaleKo.setUserId("TEST");

            MessageLocale messageLocaleEn = new MessageLocale();
            messageLocaleEn.setMsgGrpCd(message.getMsgGrpCd());
            messageLocaleEn.setMsgCd(message.getMsgCd());
            messageLocaleEn.setMsgNm("JUNIT");
            messageLocaleEn.setLangCd("en_US");
            messageLocaleEn.setUserId("TEST");

            List<MessageLocale> msgLocList = new ArrayList<>();
            msgLocList.add(messageLocaleKo);
            msgLocList.add(messageLocaleEn);

            message.setMsgLocList(msgLocList);

            MvcResult result = mvc.perform(
                post(URL).contentType(MediaType.APPLICATION_JSON_UTF8).content(jsonStringFromObject(message))).andDo(
                    print()).andExpect(status().isOk()).andExpect(
                        content().contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(
                            jsonPath("$.MSG_CD").value("JUNIT")).andReturn();

            log.info(result.getResponse().getContentAsString());

            String query = result.getResponse().getContentAsString();

            Message retrieveMessage = jsonStringToObject(query, Message.class);

            log.info(retrieveMessage.getMsgPk());

            String msgPk = retrieveMessage.getMsgPk();

            retrieveMessage.setMsgCd("JUNIT1");

            List<MessageLocale> retrieveMsgLocList = retrieveMessage.getMsgLocList();

            if(!CollectionUtils.isEmpty(retrieveMsgLocList)) {
                for(MessageLocale retrieveMsg : retrieveMsgLocList) {
                    retrieveMsg.setTransactionType("U");
                }
            }

            mvc.perform(put(URL + "/{msgPk}", msgPk).contentType(MediaType.APPLICATION_JSON_UTF8).content(
                jsonStringFromObject(retrieveMessage))).andDo(print()).andExpect(status().isOk());

            mvc.perform(delete(URL + "/{msgPk}", msgPk)).andDo(print()).andExpect(status().isOk());
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
