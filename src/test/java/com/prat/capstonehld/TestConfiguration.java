package com.prat.capstonehld;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan(basePackages = "com.prat.capstonehld")
public class TestConfiguration {
    @Bean
    @Primary
    public RestTemplate getRestTemplate()
    {
        return new RestTemplate();
    }
}
