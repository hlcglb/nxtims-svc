package com.hyundaiuni.nxtims.service.app;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundaiuni.nxtims.domain.app.Message;
import com.hyundaiuni.nxtims.exception.ServiceException;
import com.hyundaiuni.nxtims.mapper.app.MessageMapper;

@Service
public class MessageService {
    final static private Locale[] AVAILABLE_LOCALES = {new Locale("ko_KR")};

    @Autowired
    private MessageMapper messageMapper;

    @Transactional(readOnly = true)
    public List<Message> getMessagesByLanguageCode(String languageCode) {
        
        boolean isAvailableLocale = isAvailableLocale(new Locale(languageCode));
        
        if(!isAvailableLocale){
            throw new ServiceException("MSG.LOCALE_NOT_SUPPORTED", languageCode + " was not supported.");
        }
        
        List<Message> messageList = messageMapper.getMessagesByLanguageCode(languageCode);
        
        return messageList;
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
