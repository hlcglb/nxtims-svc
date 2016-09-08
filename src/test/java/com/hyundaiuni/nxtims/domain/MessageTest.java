package com.hyundaiuni.nxtims.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyundaiuni.nxtims.domain.app.Message;
import com.hyundaiuni.nxtims.domain.app.MessageLocale;

public class MessageTest {
    @Test
    public void testObjectToJson() {
        Exception ex = null;

        try {
            ObjectMapper mapper = new ObjectMapper();

            Message message = new Message();
            message.setMsgPk("0000000001");
            message.setMsgGrpCd("LAB");
            message.setMsgCd("INQUIRY");
            message.setMsgNm("조회");

            mapper.writeValueAsString(message);

            MessageLocale messageLocale = new MessageLocale();
            messageLocale.setMsgLocPk("0000000001");
            messageLocale.setMsgGrpCd("LAB");
            messageLocale.setMsgCd("INQUIRY");
            messageLocale.setLangCd("ko_KR");
            messageLocale.setMsgNm("조회");

            mapper.writeValueAsString(messageLocale);
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
            
            String json = "{\"MSG_PK\":\"0000000001\",\"MSG_GRP_CD\":\"LAB\",\"MSG_CD\":\"INQUIRY\",\"MSG_NM\":\"조회\"}";

            mapper.readValue(json, Message.class);            

            json = "{\"MSG_LOC_PK\":\"0000000001\",\"MSG_GRP_CD\":\"LAB\",\"MSG_CD\":\"INQUIRY\",\"LANG_CD\":\"ko_KR\",\"MSG_NM\":\"조회\"}";

            mapper.readValue(json, MessageLocale.class);
        }
        catch(Exception e) {
            ex = e;
        }

        assertEquals(null, ex);
    }
}
