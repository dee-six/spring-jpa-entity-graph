package com.six.mysample.springjpaentitygraph.configuration;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.six.mysample.springjpaentitygraph")
@Slf4j
public class DomainConfiguration {

    @PostConstruct
    public void logConfiguration() {
        log.info("Configuration is called");
    }
}
