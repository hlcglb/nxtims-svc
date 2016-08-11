package com.hyundaiuni.nxtims.service.sample;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleServiceTests {
    @Autowired
    private SampleService sampleService;

    @Test
    public void testGet() {
        assertThat(sampleService.get("19850003")).isNotEmpty();
    }

    @Test
    public void testInsert() {
        Map<String, Object> map = new HashMap<>();
        map.put("REMARK", "SampleServiceTests - " + new Date().toString());
        sampleService.insert(map);
    }
}
