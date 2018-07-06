package com.waggi.metersdata.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class BeanConfiguration {

    private Logger log = LoggerFactory.getLogger(BeanConfiguration.class);

    @Bean
    @Scope(BeanDefinition.SCOPE_PROTOTYPE)
    @SuppressWarnings("all")
    protected Logger logger(InjectionPoint injectionPoint){

        log.info(String.format("Create new Logger instance for %s", injectionPoint.getField().getDeclaringClass()));

        return LoggerFactory.getLogger(injectionPoint.getField().getDeclaringClass());
    }
}
