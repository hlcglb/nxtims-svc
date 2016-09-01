package com.hyundaiuni.nxtims.controller.app;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.app.ResourceService;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourceController {
    private static final Log log = LogFactory.getLog(ResourceController.class);
    
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getResources() {
        try {
            return new ResponseEntity<>(resourceService.getResources(), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            
            Map<String, String> error = new HashMap<>();
            error.put("CODE", "999");
            error.put("MESSAGE", e.getMessage());

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
