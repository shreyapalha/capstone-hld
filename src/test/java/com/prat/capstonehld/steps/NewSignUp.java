package com.prat.capstonehld.steps;



import com.prat.capstonehld.SpringIntegrationTest;
import com.prat.capstonehld.dto.AccountDto;
import com.prat.capstonehld.dto.ApiSingleRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class NewSignUp  extends SpringIntegrationTest {

    private AccountDto accountDto=new AccountDto();
    private UserDto request=new UserDto();
    private int statusCode;
    private JSONObject json;

    @LocalServerPort private int port;

    private ApiSingleRequest<UserDto> requestBody=new ApiSingleRequest<>();
    private ResponseEntity<String> response;


    @Autowired
    private AccountRepository accountRepository;
    @Autowired private UserRepository userRepository;


    private void preparedData(DataTable dataTable)
    {
        List<Map<String,String>> records=dataTable.asMaps(String.class, String.class);
        records.forEach(
                record ->{
                    String bankId= record.get("bankId");
                    double accountBalance= Double.parseDouble(record.get("balance_amount"));
                    double limitAmount= Double.parseDouble(record.get("limit_balance"));
                    double lienAmount= Double.parseDouble(record.get("lien_amount"));
                    String username=record.get("username");
                    String password=record.get("password");
                    AccountDto accountDto=new AccountDto(accountBalance,limitAmount,lienAmount);
                    UserDto userDto=new UserDto(bankId,username,password,accountDto);
                    Account account=new Account(accountDto,Long.parseLong(bankId));
                    User user=new User(userDto,account);
                    accountRepository.save(account);
                    userRepository.save(user);
                }
        );


    }

    private String createURLWithPort(String uri){
        return "http://localhost:"+port+uri;
    }

    @Given("We have balance amount {string},lien amount {string} and limit balance {string} with following data")
    public void weHaveBalanceAmountBalance_amountLienAmountLien_amountBankIdBankIdAndLimitBalanceLimit_balanceWithFollowingData(
            String balanceAmount, String lienAmount, String limitBalance,DataTable dataTable
            ) {
        preparedData(dataTable);
        double balanceAmt=Double.parseDouble(balanceAmount);
        double lienAmt=Double.parseDouble(lienAmount);
        double limitBal=Double.parseDouble(limitBalance);
        accountDto.setBalance_amount(balanceAmt);
        accountDto.setLien_amount(lienAmt);
        accountDto.setLimit_amount(limitBal);

    }

    @And("We have request data with bankId {string},username {string} password {string} with following data")
    public void weHaveRequestDataWithBankIdBankIdUsernameUsernamePasswordPasswordWithFollowingData(
            String bankId,String username,String password) {
        request.setBankId(bankId);
        request.setUsername(username);
        request.setPassword(password);
        request.setAccountDto(accountDto);

    }

    @When("We call the {string} with success")
    public void weCallTheWithSuccess(String arg0) throws JSONException {
        HttpEntity<UserDto> req=new HttpEntity<>(request);

        response=restTemplate.exchange(
                createURLWithPort("/api/signUp"), HttpMethod.POST,req, String.class);
         statusCode = response.getStatusCodeValue();
        System.out.println(statusCode);

    }



    @Then("We got a response with status of {string}")
    public void weGotAResponseWithMessageOfStatus(String status) {
        assertEquals(Integer.parseInt(status),statusCode);
    }
}
