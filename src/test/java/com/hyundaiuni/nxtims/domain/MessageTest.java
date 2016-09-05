package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.Message;

public class MessageTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            Message message = new Message();
            message.setMsgGrpCd("LAB");
            message.setMsgCd("INQUIRY");
            message.setLangCd("ko_KR");
            message.setMsgNm("조회");

            mapper.writeValueAsString(message);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testJsonToObject() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            String json = "{\"MSG_GRP_CD\":\"LAB\",\"MSG_CD\":\"INQUIRY\",\"LANG_CD\":\"ko_KR\",\"MSG_NM\":\"조회\"}";

            mapper.readValue(json, Message.class);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }
}
