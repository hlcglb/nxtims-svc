package com.hyundaiuni.nxtims.service.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundaiuni.nxtims.domain.app.Message;
import com.hyundaiuni.nxtims.domain.app.MessageLocale;
import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {
    private static final Log log = LogFactory.getLog(MessageServiceTest.class);

    @Autowired
    private MessageService messageService;

    @Test
    public void testGetMessageListByLanguageCode() {
        Exception ex = null;

        try {
            messageService.getMessageListByLanguageCode("ko_KR");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetMessageList() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("msgGrpCd", "MSG");

            messageService.getMessageListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);

        try {
            Map<String, Object> parameter = new HashMap<>();

            String msgCd = "NO_DATA_FOUND^TRANSACTION_TYPE_NOT_SUPPORTED";

            parameter.put("msgCd", msgCd);

            messageService.getMessageListByParam(parameter, 0, 10);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testGetMessageLocaleByMsgCd() {
        Exception ex = null;

        try {
            Map<String, Object> parameter = new HashMap<>();
            parameter.put("msgGrpCd", "MSG");
            parameter.put("msgCd", "NO_DATA_FOUND");

            messageService.getMessageLocaleListByParam(parameter);
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
            messageService.getMessageByMsgPk("0000000013");
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testInsertMessage() {
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

            Message retrieveMessage = messageService.insertMessage(message);

            messageService.deleteMessage(retrieveMessage.getMsgPk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }

    @Test
    public void testInsertMessageMasterDupError() {
        try {
            Message message = new Message();

            message.setMsgGrpCd("LAB");
            message.setMsgCd("INQUIRY");

            messageService.insertMessage(message);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("duplicated");
        }
    }

    @Test
    public void testInsertMessageDetailDupError() {
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
            messageLocaleEn.setLangCd("ko_KR");
            messageLocaleEn.setUserId("TEST");

            List<MessageLocale> msgLocList = new ArrayList<>();
            msgLocList.add(messageLocaleKo);
            msgLocList.add(messageLocaleEn);

            message.setMsgLocList(msgLocList);

            messageService.insertMessage(message);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            assertThat(e).isInstanceOf(ServiceException.class).hasMessageContaining("duplicated");
        }
    }

    @Test
    public void testUpdateMessage() {
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

            Message retrieveMessage = messageService.insertMessage(message);

            List<MessageLocale> retrieveMsgLocList = retrieveMessage.getMsgLocList();

            if(CollectionUtils.isNotEmpty(retrieveMsgLocList)) {
                for(MessageLocale retrieveMsg : retrieveMsgLocList) {
                    retrieveMsg.setTransactionType("U");
                }
            }

            messageService.updateMessage(retrieveMessage.getMsgPk(), retrieveMessage);
            messageService.deleteMessage(retrieveMessage.getMsgPk());
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }

        assertEquals(null, ex);
    }
}
