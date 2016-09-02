package com.hyundaiuni.nxtims.service;

import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.hyundaiuni.nxtims.helper.ObjectHelper;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class ProfilingRunningTimeAspect {
    @Autowired
    ProfilingRunningTimeTask profilingRunningTimeTask;

    @Around("execution(* com.hyundaiuni.nxtims.service..*Service.*(..))")
    public Object doAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();

        Date startDate = new Date();
        stopWatch.start();

        try {
            return joinPoint.proceed();
        }
        finally {
            stopWatch.stop();

            profilingRunningTimeTask.profiling(joinPoint.getSignature().toString(),
                ObjectHelper.toString(joinPoint.getArgs(), "null"), startDate, stopWatch.getTotalTimeMillis());
        }
    }
}