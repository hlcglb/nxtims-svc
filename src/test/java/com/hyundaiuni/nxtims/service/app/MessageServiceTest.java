package com.hyundaiuni.nxtims.service.app;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.hyundaiuni.nxtims.domain.app.Message;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageServiceTest {
    @Autowired
    private MessageService messageService;
    
    @Test
    public void testGetMessagesByLanguageCode(){
        List<Message> messageList = messageService.getMessagesByLanguageCode("ko_KR");
        
        if(CollectionUtils.isEmpty(messageList)){
            fail("");
        }        
    }
}
