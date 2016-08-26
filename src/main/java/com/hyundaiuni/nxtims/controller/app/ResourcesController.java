package com.hyundaiuni.nxtims.controller.app;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.app.ResourcesService;

@RestController
@RequestMapping("/api/v1/resources")
public class ResourcesController {
    @Autowired
    private ResourcesService resourcesService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getResources() {
        try {
            return new ResponseEntity<>(resourcesService.getResources(), HttpStatus.OK);
        }
        catch(Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("CODE", "999");
            error.put("MESSAGE", e.getMessage());

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }
}
