package com.hyundaiuni.nxtims.handler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hyundaiuni.nxtims.exception.ServiceException;

@ControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        Map<String, String> error = new HashMap<>();

        error.put("CODE", "UNDEFINED");
        error.put("MESSAGE", e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<?> handleServiceException(ServiceException e) {
        Map<String, String> error = new HashMap<>();

        error.put("CODE", e.getCode());
        error.put("MESSAGE", e.getMessage());
        error.put("ARGUMENTS", StringUtils.collectionToDelimitedString(CollectionUtils.arrayToList(e.getArgs()), "^"));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
