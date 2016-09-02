package com.hyundaiuni.nxtims.service;

import static org.junit.Assert.fail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProfilingMethodExecutionTimeTaskTest {
    private static final Log log = LogFactory.getLog(ProfilingMethodExecutionTimeTaskTest.class);

    @Autowired
    private ProfilingMethodExecutionTimeTask profilingMethodExecutionTimeService;

    @Test
    public void createLog() {
        try {
            profilingMethodExecutionTimeService.profiling("TEST", "args[0]=1", "20160902131409", (long)0.004, null);
        }
        catch(Exception e) {
            log.error(e.getMessage());
            fail(e.getMessage());
        }
    }
}
