package com.prat.capstonehld.steps;

import com.prat.capstonehld.TestConfiguration;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@ContextConfiguration(classes = TestConfiguration.class)
public class DemoStep {

    @Autowired
    private MockMvc mvc;

    ResultActions action;


    @When("the client calls \\/api\\/get")
    public void theClientCallsApiGet() throws Exception {
        action=mvc.perform(get("/api/get").contentType(MediaType.APPLICATION_JSON));
    }

    @Then("the client will get get status code of {int}")
    public void theClientWillGetGetStatusCodeOf(int status) throws Exception {
        action.andExpect(status().is(status));
    }

}
