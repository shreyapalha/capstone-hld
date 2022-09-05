package com.prat.capstonehld.runner;

import com.prat.capstonehld.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@ActiveProfiles("test")
public class SpringIntegrationTest {

    @Autowired protected RestTemplate restTemplate;
    @Autowired protected AccountRepository accountRepository;
    @LocalServerPort private int port;

    public SpringIntegrationTest()
    {
        restTemplate=new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
}