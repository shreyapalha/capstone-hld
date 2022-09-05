package com.prat.capstonehld.config;

import com.prat.capstonehld.CapstoneHldApplication;
import io.cucumber.junit.Cucumber;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@CucumberContextConfiguration
@SpringBootTest(
        classes = {CapstoneHldApplication.class, Cucumber.class},
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
    @MockBean private RestTemplate restTemplate;
}