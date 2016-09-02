package com.hyundaiuni.nxtims.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfilingRunningTimeTaskTest {
    private static final Log log = LogFactory.getLog(ProfilingRunningTimeTaskTest.class);

    @Autowired
    private ProfilingRunningTimeTask profilingRunningTimeTask;

    @Test
    public void createLog() {
        Exception ex = null;
        
        try {
            profilingRunningTimeTask.profiling("TEST", "args[0]=1", new Date(), (long)0.004);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            ex = e;
        }
        
        assertEquals(null, ex);
    }
}
