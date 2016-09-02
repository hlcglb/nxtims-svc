package com.hyundaiuni.nxtims.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.hyundaiuni.nxtims.mapper.app.ServiceLogMapper;

@Service
public class ProfilingMethodExecutionTimeTask {
    private static final Log log = LogFactory.getLog(ProfilingMethodExecutionTimeTask.class);

    @Autowired
    private ServiceLogMapper serviceLogMapper;

    @Async("taskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void profiling(String method, String args, String startDateTime, long totalTimeMillis, String errorMessage) {
        Assert.notNull(method, "method must not be null");

        if(log.isDebugEnabled()) {
            log.debug("Method = [" + method + "]");
            log.debug("Arguments = [" + args + "]");
            log.debug("Start date = [" + startDateTime + "]");
            log.debug("Total Running Time [" + totalTimeMillis + "]");
            log.debug("Error message [" + errorMessage + "]");
        }

        Map<String, Object> parameter = new HashMap<>();
        parameter.put("SERVICE_ID", method);
        parameter.put("ARGUMENTS", args);
        parameter.put("START_DATE_TIME", startDateTime);
        parameter.put("TOTAL_RUNNING_TIME", totalTimeMillis);
        parameter.put("ERROR_MESSAGE", errorMessage);

        serviceLogMapper.createLog(parameter);
    }
}
