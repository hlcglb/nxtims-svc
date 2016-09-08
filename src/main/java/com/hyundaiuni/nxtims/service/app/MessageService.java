package com.hyundaiuni.nxtims.service.app;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.domain.app.Message;
import com.hyundaiuni.nxtims.domain.app.MessageLocale;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.MessageMapper;

@Service
public class MessageService {
    final static private Locale[] AVAILABLE_LOCALES = {new Locale("ko_KR")};

    @Autowired
    private MessageMapper messageMapper;

    @Transactional(readOnly = true)
    public List<MessageLocale> getMessageListByLanguageCode(String languageCode) {

        boolean isAvailableLocale = isAvailableLocale(new Locale(languageCode));

        if(!isAvailableLocale) {
            throw new ServiceException("MSG.LOCALE_NOT_SUPPORTED", languageCode + " was not supported.");
        }

        List<MessageLocale> messageList = messageMapper.getMessageListByLanguageCode(languageCode);

        return messageList;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessageListByParam(Map<String, Object> parameter, int offset, int limit) {
        if(StringUtils.isNotEmpty(MapUtils.getString(parameter, "msgCd"))) {
            String[] msgCdArray = StringUtils.split(MapUtils.getString(parameter, "msgCd"), "^");
            parameter.put("msgCdArray", msgCdArray);
        }

        parameter.put("offset", offset);
        parameter.put("limit", limit);

        return messageMapper.getMessageListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public List<MessageLocale> getMessageLocaleListByParam(Map<String, Object> parameter) {
        return messageMapper.getMessageLocaleListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public Message getMessageByMsgPk(String msgPk) {
        Message message = messageMapper.getMessageByMsgPk(msgPk);

        if(message == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", msgPk + " not found.");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("msgGrpCd", message.getMsgGrpCd());
        parameter.put("msgCd", message.getMsgCd());

        List<MessageLocale> msgLocList = messageMapper.getMessageLocaleListByParam(parameter);

        message.setMsgLocList(msgLocList);

        return message;
    }

    @Transactional
    public Message insertMessage(Message message) {
        if(StringUtils.isEmpty(message.getMsgGrpCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_GRP_CD must not be null.");
        }

        if(StringUtils.isEmpty(message.getMsgCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_CD must not be null.");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("msgGrpCd", message.getMsgGrpCd());
        parameter.put("msgCd", message.getMsgCd());

        Message tempMessage = messageMapper.getMessageByParam(parameter);

        if(tempMessage != null) {
            throw new ServiceException("MSG.DUPLICATED", message.toString() + " was duplicated.");
        }

        String msgPk = messageMapper.getMessageSequence();

        message.setMsgPk(msgPk);

        messageMapper.insertMessage(message);

        List<MessageLocale> msgLocList = message.getMsgLocList();

        if(CollectionUtils.isNotEmpty(msgLocList)) {
            for(MessageLocale messageLocale : msgLocList) {
                if(StringUtils.isEmpty(messageLocale.getLangCd())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "LANG_CD must not be null.");
                }

                if(StringUtils.isEmpty(messageLocale.getMsgNm())) {
                    throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_NM must not be null.");
                }

                parameter = new HashMap<>();
                parameter.put("msgGrpCd", message.getMsgGrpCd());
                parameter.put("msgCd", message.getMsgCd());
                parameter.put("langCd", messageLocale.getLangCd());

                MessageLocale tempMessageLocale = messageMapper.getMessageLocaleByParam(parameter);

                if(tempMessageLocale != null) {
                    throw new ServiceException("MSG.DUPLICATED", tempMessageLocale.toString() + " was duplicated.");
                }

                messageLocale.setMsgGrpCd(message.getMsgGrpCd());
                messageLocale.setMsgCd(message.getMsgCd());

                messageMapper.insertMessageLocale(messageLocale);
            }
        }

        return messageMapper.getMessageByMsgPk(msgPk);
    }

    @Transactional
    public void updateMessage(String msgPk, Message message) {
        Message tempMessage = messageMapper.getMessageByMsgPk(msgPk);

        if(tempMessage == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", msgPk + " not found.");
        }

        if(StringUtils.isEmpty(message.getMsgPk())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_PK must not be null.");
        }

        if(StringUtils.isEmpty(message.getMsgGrpCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_GRP_CD must not be null.");
        }

        if(StringUtils.isEmpty(message.getMsgCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_CD must not be null.");
        }
        
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("msgGrpCd", tempMessage.getMsgGrpCd());
        parameter.put("msgCd", tempMessage.getMsgCd());        
        
        List<MessageLocale> tempMessageLocaleList = messageMapper.getMessageLocaleListByParam(parameter);
        
        if(CollectionUtils.isNotEmpty(tempMessageLocaleList)) {
            for(MessageLocale messageLocale : tempMessageLocaleList) {
                messageLocale.setMsgGrpCd(message.getMsgGrpCd());
                messageLocale.setMsgCd(message.getMsgCd());
                
                messageMapper.updateMessageLocale(messageLocale);
            }
        }        

        messageMapper.updateMessage(message);

        List<MessageLocale> msgLocList = message.getMsgLocList();

        if(CollectionUtils.isNotEmpty(msgLocList)) {
            for(MessageLocale messageLocale : msgLocList) {
                String messageLocaleTransactionType = messageLocale.getTransactionType();

                messageLocale.setMsgGrpCd(message.getMsgGrpCd());
                messageLocale.setMsgCd(message.getMsgCd());

                if("C".equals(messageLocaleTransactionType) || "U".equals(messageLocaleTransactionType)) {
                    if(StringUtils.isEmpty(messageLocale.getLangCd())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "LANG_CD must not be null.");
                    }

                    if(StringUtils.isEmpty(messageLocale.getMsgCd())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_NM must not be null.");
                    }
                }

                if("U".equals(messageLocaleTransactionType) || "D".equals(messageLocaleTransactionType)) {
                    if(StringUtils.isEmpty(messageLocale.getMsgLocPk())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_LOC_PK must not be null.");
                    }
                }

                if("C".equals(messageLocaleTransactionType)) {
                    messageMapper.insertMessageLocale(messageLocale);
                }
                else if("U".equals(messageLocaleTransactionType)) {
                    messageMapper.updateMessageLocale(messageLocale);
                }
                else if("D".equals(messageLocaleTransactionType)) {
                    messageMapper.deleteMessageByMsgPk(messageLocale.getMsgLocPk());
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        messageLocaleTransactionType + " was not supported.");
                }
            }
        }
    }

    @Transactional
    public void deleteMessage(String msgPk) {
        if(StringUtils.isEmpty(msgPk)) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_PK must not be null.");
        }

        Message message = messageMapper.getMessageByMsgPk(msgPk);

        if(message == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", msgPk + " not found.");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("msgGrpCd", message.getMsgGrpCd());
        parameter.put("msgCd", message.getMsgCd());

        messageMapper.deleteMessageLocaleByParam(parameter);
        messageMapper.deleteMessageByMsgPk(msgPk);
    }

    private boolean isAvailableLocale(Locale locale) {
        for(Locale availableLocale : AVAILABLE_LOCALES) {
            if(availableLocale.equals(locale)) {
                return true;
            }
        }

        return false;
    }
}
