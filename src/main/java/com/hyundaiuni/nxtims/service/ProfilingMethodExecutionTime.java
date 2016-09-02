package com.hyundaiuni.nxtims.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import com.hyundaiuni.nxtims.exception.ServiceException;

@Component
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE)
public class ProfilingMethodExecutionTime {
    @Value("${system.service.running-time.timeout-second}")
    private double timeoutSecond;

    @Autowired
    ProfilingMethodExecutionTimeTask profilingMethodExecutionTimeTask;

    @Around("execution(* com.hyundaiuni.nxtims.service..*Service.*(..))")
    public Object doAround(final ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start(joinPoint.toShortString());

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String startDateTime = dateFormat.format(new Date());

        boolean isExceptionThrown = false;
        String errorMessage = null;

        try {
            return joinPoint.proceed();
        }
        catch(ServiceException e) {
            isExceptionThrown = true;
            errorMessage = e.getMessage();

            throw e;
        }
        catch(RuntimeException e) {
            isExceptionThrown = true;
            errorMessage = e.getMessage();

            throw new ServiceException("UNDEFINED", e.getMessage(), e);
        }
        finally {
            stopWatch.stop();

            boolean isProfiling = false;

            if(stopWatch.getTotalTimeSeconds() > timeoutSecond || isExceptionThrown) {
                isProfiling = true;
            }

            if(isProfiling) {
                String arguments = argumentsToString(joinPoint.getArgs());

                profilingMethodExecutionTimeTask.profiling(joinPoint.getSignature().toString(), arguments,
                    startDateTime, stopWatch.getTotalTimeMillis(), errorMessage);
            }
        }
    }

    private String argumentsToString(Object[] args) {
        StringBuilder builder = new StringBuilder();

        if(args != null) {
            final int len = args.length;
            int i = 0;

            while(i < len) {
                if(args[i] != null) {
                    builder.append("args[" + i + "]=" + args[i].toString());
                }
                else {
                    builder.append("args[" + i + "]= null");
                }

                if(i < len - 1) {
                    builder.append(",");
                }

                i++;
            }
        }

        return builder.toString();
    }
}