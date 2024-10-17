package com.arivanamin.library.backend.core.infrastructure.aspects;

import com.arivanamin.library.backend.core.domain.aspects.ExecuteAndLogPerformance;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SpringSchedulerLoggingAspect {
    
    @Around ("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object logScheduler (ProceedingJoinPoint joinPoint) throws Throwable {
        return ExecuteAndLogPerformance.executeThrowable(initJobName(joinPoint),
            joinPoint::proceed);
    }
    
    private String initJobName (JoinPoint joinPoint) {
        final String classPath = joinPoint.getSignature().getDeclaringTypeName();
        return "The scheduled task: %s".formatted(classPath);
    }
}
