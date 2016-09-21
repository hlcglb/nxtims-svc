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
import org.springframework.util.Assert;

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
        Assert.notNull(languageCode, "languageCode must not be null");

        boolean isAvailableLocale = isAvailableLocale(new Locale(languageCode));

        if(!isAvailableLocale) {
            throw new ServiceException("MSG.LOCALE_NOT_SUPPORTED", languageCode + " was not supported.",
                new String[] {languageCode});
        }

        List<MessageLocale> messageList = messageMapper.getMessageListByLanguageCode(languageCode);

        return messageList;
    }

    @Transactional(readOnly = true)
    public List<Message> getMessageListByParam(Map<String, Object> parameter, int offset, int limit) {
        Assert.notNull(parameter, "parameter must not be null");
        Assert.notNull(offset, "offset must not be null");
        Assert.notNull(limit, "limit must not be null");

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
        Assert.notNull(parameter, "parameter must not be null");

        return messageMapper.getMessageLocaleListByParam(parameter);
    }

    @Transactional(readOnly = true)
    public Message getMessage(String msgPk) {
        Assert.notNull(msgPk, "msgPk must not be null");

        Message message = messageMapper.getMessageByMsgPk(msgPk);

        if(message == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", msgPk + " not found.", null);
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("msgGrpCd", message.getMsgGrpCd());
        parameter.put("msgCd", message.getMsgCd());

        message.setMsgLocList(messageMapper.getMessageLocaleListByParam(parameter));

        return message;
    }

    @Transactional
    public Message insertMessage(Message message) {
        Assert.notNull(message, "message must not be null");

        checkMandatoryMessage(message);

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("msgGrpCd", message.getMsgGrpCd());
        parameter.put("msgCd", message.getMsgCd());

        Message tempMessage = messageMapper.getMessageByParam(parameter);

        if(tempMessage != null) {
            throw new ServiceException("MSG.DUPLICATED", message.toString() + " was duplicated.", null);
        }

        message.setMsgPk(messageMapper.getMessageSequence());

        messageMapper.insertMessage(message);

        List<MessageLocale> msgLocList = message.getMsgLocList();

        if(CollectionUtils.isNotEmpty(msgLocList)) {
            for(MessageLocale messageLocale : msgLocList) {
                checkMandatoryMessageLocale(messageLocale);

                messageLocale.setMsgGrpCd(message.getMsgGrpCd());
                messageLocale.setMsgCd(message.getMsgCd());

                parameter = new HashMap<>();
                parameter.put("msgGrpCd", messageLocale.getMsgGrpCd());
                parameter.put("msgCd", messageLocale.getMsgCd());
                parameter.put("langCd", messageLocale.getLangCd());

                MessageLocale tempMessageLocale = messageMapper.getMessageLocaleByParam(parameter);

                if(tempMessageLocale != null) {
                    throw new ServiceException("MSG.DUPLICATED", tempMessageLocale.toString() + " was duplicated.",
                        null);
                }

                messageMapper.insertMessageLocale(messageLocale);
            }
        }

        return getMessage(message.getMsgPk());
    }

    @Transactional
    public void updateMessage(Message message) {
        Assert.notNull(message, "message must not be null");

        if(StringUtils.isEmpty(message.getMsgPk())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_PK must not be null.", new String[] {"MSG_PK"});
        }

        Message tempMessage = messageMapper.getMessageByMsgPk(message.getMsgPk());

        if(tempMessage == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", message.getMsgPk() + " not found.", null);
        }

        checkMandatoryMessage(message);

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
                messageLocale.setMsgGrpCd(message.getMsgGrpCd());
                messageLocale.setMsgCd(message.getMsgCd());

                String messageLocaleTransactionType = messageLocale.getTransactionType();

                parameter = new HashMap<>();
                parameter.put("msgGrpCd", messageLocale.getMsgGrpCd());
                parameter.put("msgCd", messageLocale.getMsgCd());
                parameter.put("langCd", messageLocale.getLangCd());

                if("C".equals(messageLocaleTransactionType)) {
                    checkMandatoryMessageLocale(messageLocale);

                    if(messageMapper.getMessageLocaleByParam(parameter) != null) {
                        throw new ServiceException("MSG.DUPLICATED", parameter.toString() + " was duplicated.",
                            null);
                    }

                    messageMapper.insertMessageLocale(messageLocale);
                }
                else if("U".equals(messageLocaleTransactionType)) {
                    checkMandatoryMessageLocale(messageLocale);

                    if(StringUtils.isEmpty(messageLocale.getMsgLocPk())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_LOC_PK must not be null.",
                            new String[] {"MSG_LOC_PK"});
                    }

                    if(messageMapper.getMessageLocaleByParam(parameter) == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }

                    messageMapper.updateMessageLocale(messageLocale);
                }
                else if("D".equals(messageLocaleTransactionType)) {
                    if(StringUtils.isEmpty(messageLocale.getMsgLocPk())) {
                        throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_LOC_PK must not be null.",
                            new String[] {"MSG_LOC_PK"});
                    }

                    if(messageMapper.getMessageLocaleByParam(parameter) == null) {
                        throw new ServiceException("MSG.NO_DATA_FOUND", parameter.toString() + " not found.", null);
                    }

                    messageMapper.deleteMessageByMsgPk(messageLocale.getMsgLocPk());
                }
                else {
                    throw new ServiceException("MSG.TRANSACTION_TYPE_NOT_SUPPORTED",
                        messageLocaleTransactionType + " was not supported.", null);
                }
            }
        }
    }

    @Transactional
    public void deleteMessage(String msgPk) {
        Assert.notNull(msgPk, "msgPk must not be null");

        Message message = messageMapper.getMessageByMsgPk(msgPk);

        if(messageMapper.getMessageByMsgPk(msgPk) == null) {
            throw new ServiceException("MSG.NO_DATA_FOUND", msgPk + " not found.", null);
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

    private void checkMandatoryMessage(Message message) {
        if(StringUtils.isEmpty(message.getMsgGrpCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_GRP_CD must not be null.",
                new String[] {"MSG_GRP_CD"});
        }

        if(StringUtils.isEmpty(message.getMsgCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_CD must not be null.", new String[] {"MSG_CD"});
        }
    }

    private void checkMandatoryMessageLocale(MessageLocale messageLocale) {
        if(StringUtils.isEmpty(messageLocale.getLangCd())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "LANG_CD must not be null.", new String[] {"LANG_CD"});
        }

        if(StringUtils.isEmpty(messageLocale.getMsgNm())) {
            throw new ServiceException("MSG.MUST_NOT_NULL", "MSG_NM must not be null.", new String[] {"MSG_NM"});
        }
    }
}
