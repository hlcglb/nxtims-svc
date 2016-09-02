package com.hyundaiuni.nxtims.service;

import java.util.Date;
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
public class ProfilingRunningTimeTask {
    private static final Log log = LogFactory.getLog(ProfilingRunningTimeTask.class);
    
    @Autowired
    private ServiceLogMapper serviceLogMapper;

    @Async("taskExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void profiling(String serviceId, String args, Date startDate, long totalTimeMillis) {
        Assert.notNull(serviceId, "serviceId must not be null");
        Assert.notNull(args, "args must not be null");
        Assert.notNull(startDate, "startDateTime must not be null");
        Assert.notNull(totalTimeMillis, "totalTimeMillis must not be null");
        
        if(log.isInfoEnabled()) {
            log.info("Method = [" + serviceId + "]");
            log.info("Arguments = [" + args + "]");
            log.info("Start date = [" + startDate + "]");
            log.info("Total Running Time [" + totalTimeMillis + "]");
        }        
        
        Map<String, Object> parameter = new HashMap<>();
        parameter.put("SERVICE_ID", serviceId);
        parameter.put("ARGUMENTS", args);
        parameter.put("START_DATE", startDate);
        parameter.put("TOTAL_RUNNING_TIME", totalTimeMillis);

        serviceLogMapper.createLog(parameter);
    }
}
