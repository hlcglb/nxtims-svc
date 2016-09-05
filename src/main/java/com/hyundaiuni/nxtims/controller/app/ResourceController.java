package com.hyundaiuni.nxtims.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.app.ResourceService;

@RestController
@RequestMapping("/api/v1/app/resources")
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getResources() {
        return new ResponseEntity<>(resourceService.getResources(), HttpStatus.OK);
    }
}
