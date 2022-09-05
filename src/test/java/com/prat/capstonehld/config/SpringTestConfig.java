package com.prat.capstonehld.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.prat.capstonehld")
public class SpringTestConfig {
    @Bean
    @Primary
    public RestTemplate getRestTemplate(){return new RestTemplate();}
}
