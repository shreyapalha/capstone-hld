package com.prat.capstonehld.steps;

import com.prat.capstonehld.modal.Account;
import com.prat.capstonehld.repository.AccountRepository;
import com.prat.capstonehld.repository.UserRepository;
import com.prat.capstonehld.runner.SpringIntegrationTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.junit.CucumberOptions;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetBalanceStep extends SpringIntegrationTest {

    @Autowired private AccountRepository accountRepository;
    @Autowired private RestTemplate restTemplate;

    String baseUri;
    private int statusCodeValue;
    private ResponseEntity<Double> response;

    @LocalServerPort private int port;
    private UriComponentsBuilder builder;
    private JSONObject json;

    private String createURLWithPort(String uri)
    {
        return "http://localhost:"+port+uri;
    }

    private HttpHeaders preparedHeaders()
    {
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    private void preparedData(DataTable dataTable)
    {
        List<Map<String,String>> records=dataTable.asMaps(String.class, String.class);
        records.forEach(
                record ->{
                    long id= Long.parseLong(record.get("accountId"));
                    long bankId= Long.parseLong(record.get("bankId"));
                    double accountBalance= Double.parseDouble(record.get("accountBalance"));
                    double limitAmount= Double.parseDouble(record.get("limitAmount"));
                    double lienAmount= Double.parseDouble(record.get("lienAmount"));
                    Account account=new Account(id,bankId,accountBalance,limitAmount,lienAmount);

                    accountRepository.save(account);
                }

        );


    }


    @Given("We have request account id {string} and following data in the database:")
    public void weHaveRequestAccountIdAccountIdAndFollowingDataInTheDatabase(String accountId,DataTable dbData) {
        preparedData(dbData);
        long id= Long.parseLong(accountId);
        baseUri="/api/account/enquire/"+id;
        baseUri=createURLWithPort(baseUri);
    }

    @When("For Fetch we call the {string} with the request params")
    public void forFetchWeCallTheWithTheRequestParams(String arg0) throws JSONException {
        HttpEntity<?> req=new HttpEntity<>(preparedHeaders());
        try{
            response= restTemplate.exchange(builder.toUriString(), HttpMethod.GET,req, Double.class);
            statusCodeValue=response.getStatusCodeValue();
            json=new JSONObject(response.getBody().toString());

        } catch (HttpStatusCodeException e) {
            statusCodeValue=e.getRawStatusCode();
            json=new JSONObject(e.getResponseBodyAsString());
        }
    }

    @Then("For Fetch we get response with a httpStatusCode of {string}")
    public void forFetchWeGetResponseWithAHttpStatusCodeOfStatusCode(String statusCode) {
        assertEquals(Integer.parseInt(statusCode),statusCodeValue);
    }


    @Then("For we get a Validation Exception with message {string} and errorCode {string}")
    public void forWeGetAValidationExceptionWithMessageMessageAndErrorCodeErrorCode(String message,String errorCode) throws JSONException {
        if(!message.equals("NA"))
        {
            assertEquals(
                    json.getJSONObject("errorResponse")
                            .getJSONArray("validationErrors")
                            .getJSONObject(0)
                            .getString("message"),
                    message);
        }
        if(!errorCode.equals("NA"))
        {
            assertEquals(
                    json.getJSONObject("errorResponse")
                            .getJSONArray("validationErrors")
                            .getJSONObject(0)
                            .getString("code"),
                    errorCode);
        }
    }

    @Then("For Fetch we get a Wrong Data Type Exception with message {string} and errorCode {string}")
    public void forFetchWeGetAValidationExceptionWithMessageMessageAndErrorCodeErrorCode(String message,String errorCode) throws JSONException {
        if(!message.equals("NA"))
        {
            assertEquals(json.getJSONObject("errorResponse").getString("message"),message);
        }
        if(!errorCode.equals("NA"))
        {
            assertEquals(json.getJSONObject("errorResponse").getString("errorCode"),errorCode);
        }
    }
}
