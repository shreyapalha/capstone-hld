package com.prat.capstonehld.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.prat.capstonehld.TestConfiguration;
import com.prat.capstonehld.dto.AccountDto;
import com.prat.capstonehld.dto.UserDto;
import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.modal.User;
import com.prat.capstonehld.repository.AccountRepository;
import com.prat.capstonehld.repository.UserRepository;
import com.prat.capstonehld.service.UserService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfiguration.class)
public class SignUpSteps {

    private UserDto userDto=new UserDto();
    private AccountDto accountDto=new AccountDto();
    private int response;

    @Autowired
    private MockMvc mvc;

    ResultActions action;


    @Given("We have balance amount {string},lien amount {string} and limit balance {string}")
    public void weHaveBalanceAmountBalance_amountLienAmountLien_amountAndLimitBalanceLimit_balance(
            String balanceAmount,String lienAmount,String limitBalance
    ) {

        double balanceAmt=Double.parseDouble(balanceAmount);
        double lienAmt=Double.parseDouble(lienAmount);
        double limitBal=Double.parseDouble(limitBalance);
        accountDto.setBalance_amount(balanceAmt);
        accountDto.setLien_amount(lienAmt);
        accountDto.setLimit_amount(limitBal);

    }


    @Given("We have request data with bankId {string},username {string} password {string}")
    public void weHaveRequestDataWithBankIdBankIdUsernameUsernamePasswordPassword(
            String bankId,String username,String password) {
        userDto.setBankId(bankId);
        userDto.setUsername(username);
        userDto.setPassword(password);
        userDto.setAccountDto(accountDto);

    }

    @When("We call the {string}")
    public void weCallThe(String arg0) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/signUp")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto));

        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
        response = mvcResult.getResponse().getStatus();

        System.out.println("ikjkj");
    }

    @Then("We got response with status of {string}")
    public void weGotResponseWithStatusOfStatusCode(String value) {
        int status = Integer.parseInt(value);
        assertEquals(status,response);

    }



}
