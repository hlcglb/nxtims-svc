package com.hyundaiuni.nxtims.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.app.MessageService;

@RestController
@RequestMapping("/api/v1/app/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @RequestMapping(params="inquiry=getMessagesByLanguageCode", method = RequestMethod.GET)
    public ResponseEntity<?> getMessagesByLanguageCode(@RequestParam("languageCode") String languageCode) {
        Assert.notNull(languageCode, "languageCode must not be null");

        return new ResponseEntity<>(messageService.getMessagesByLanguageCode(languageCode), HttpStatus.OK);
    }
}
