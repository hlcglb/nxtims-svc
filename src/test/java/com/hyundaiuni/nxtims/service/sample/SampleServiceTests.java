package com.hyundaiuni.nxtims.service.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hyundaiuni.nxtims.exception.ServiceException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTests {
    @Autowired
    private SampleService sampleService;

    @Test
    public void testGetOk() {
        assertThat(sampleService.get("test")).isNotEmpty();
    }
    
    @Test
    public void testGetError() {
        Exception ex = null;
        
        try {
            sampleService.get("testx");
        }
        catch(Exception e) {
            ex = e;
        }
        
        if(!(ex instanceof ServiceException)){
            fail("");
        }
    }    

    @Test
    public void testInsert() {
        Map<String, Object> map = new HashMap<>();
        map.put("REMARK", "SampleServiceTests - " + new Date().toString());
        sampleService.insert(map);
    }
}
