package com.waggi.metersdata.aspect;

import com.waggi.metersdata.utils.Utils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingControllersAspect {

    @Autowired
    private Logger log;

    @Pointcut("execution(* com.waggi.metersdata.controller..*Controller.*(..))")
    private void controllers() {}

    @Before("controllers()")
    public void log(JoinPoint joinPoint) {

        log.info(String.format("Invoke controller method |>%s<| in |>%s<| class %s", joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getName(),
                Utils.concatenateStringStream(Arrays.stream(joinPoint.getArgs())
                        .map(arg -> String.format("{type = %s | value = %s}", arg.getClass().getSimpleName(), arg)),":")));
    }
}
