package com.hyundaiuni.nxtims.mapper.app;

import java.util.List;

import com.hyundaiuni.nxtims.domain.app.Message;

public interface MessageMapper {
    public List<Message> getMessagesByLanguageCode(String languageCode);
}
