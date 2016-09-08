package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;
import java.util.Map;

import com.hyundaiuni.nxtims.domain.app.Message;
import com.hyundaiuni.nxtims.domain.app.MessageLocale;

public interface MessageMapper {
    public List<MessageLocale> getMessageListByLanguageCode(String languageCode);
    
    public List<Message> getMessageListByParam(Map<String, Object> parameter);
    
    public Message getMessageByMsgPk(String msgPk);
    
    public Message getMessageByParam(Map<String, Object> parameter);
    
    public String getMessageSequence();
    
    public void insertMessage(Message message);

    public void updateMessage(Message message);

    public void deleteMessageByMsgPk(String msgPk);
    
    public MessageLocale getMessageLocaleByMsgLocPk(String msgLocPk);
    
    public MessageLocale getMessageLocaleByParam(Map<String, Object> parameter);
    
    public void insertMessageLocale(MessageLocale messageLocale);

    public void updateMessageLocale(MessageLocale messageLocale);
    
    public void deleteMessageLocaleByMsgLocPk(String msgLocPk);
    
    public List<MessageLocale> getMessageLocaleListByParam(Map<String, Object> parameter);
    
    public void deleteMessageLocaleByParam(Map<String, Object> parameter);
}
