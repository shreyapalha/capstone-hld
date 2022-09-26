package com.prat.capstonehld;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;


public class SpringIntegrationTest {

    protected RestTemplate restTemplate=new RestTemplate();

    public SpringIntegrationTest()
    {
        restTemplate=new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }
}
