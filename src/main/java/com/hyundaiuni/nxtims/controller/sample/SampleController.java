package com.hyundaiuni.nxtims.controller.sample;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.controller.app.ResourceController;
import com.hyundaiuni.nxtims.service.sample.SampleService;

@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {
    private static final Log log = LogFactory.getLog(ResourceController.class);
    
    @Autowired
    private SampleService sampleService;
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@PathVariable("id") String id) {
        try {
            return new ResponseEntity<>(sampleService.get(id), HttpStatus.OK);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            
            Map<String, String> error = new HashMap<>();
            error.put("CODE", "999");
            error.put("MESSAGE", e.getMessage());

            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }        
    }

    @RequestMapping(method = RequestMethod.POST)
    public void insert(@RequestBody Map<String, Object> map) {
        sampleService.insert(map);
    }
}
