package com.hyundaiuni.nxtims.controller.sample;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hyundaiuni.nxtims.service.sample.SampleService;

@RestController
@RequestMapping("/api/v1/sample")
public class SampleController {
    @Autowired
    private SampleService sampleService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Map<String, Object> get(@PathVariable("id") String id) {
        return sampleService.get(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void insert(@RequestBody Map<String, Object> map) {
        sampleService.insert(map);
    }
}
