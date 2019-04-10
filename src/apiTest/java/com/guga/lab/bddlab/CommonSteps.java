package com.guga.lab.bddlab;

import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ContextConfiguration(classes = {BddlabApplication.class},
        loader = SpringBootContextLoader.class)

public class CommonSteps {

    private static final String BASE_URL = "http://localhost:";
    @LocalServerPort
    private int localPort;
    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<String> response;
    private String authorizationToken;
    private WireMockServer server;


    @Then("^I the converted amount should be (.*) CLP$")
    public void iGetTheCalculationAsCLP(String amount) throws Throwable {
        Assert.assertEquals(response.getBody(), amount);
    }

    @PostConstruct
    public void initStubs() throws Exception {
        if (server == null ) {

            String rates_20171203 = readFile("rates_2017-12-03.json");
            String rates_20180203 = readFile("rates_2018-02-03.json");

            server = new WireMockServer(1580);
            configureFor("localhost", 1580);
            server.start();


            stubFor(get("/api/2017-12-03?access_key=005769388f0f40e17369dee738f22974")
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(rates_20171203)));

            stubFor(get("/api/2014-02-03?access_key=005769388f0f40e17369dee738f22974")
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(rates_20180203)));
        }
    }



    @When("^I make a conversion with (\\d+) USD and date (.*)$")
    public void iCallTheConversionServiceWithUSDAndData(double amount, String date) throws Throwable {
        URI url = UriComponentsBuilder.newInstance().scheme("http")
                .host("localhost")
                .port(localPort)
                .path("/convert")
                .queryParam("from", "USD")
                .queryParam("amount", amount)
                .queryParam("date", date)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if(server!=null){
            server.stop();
        }
    }

    @And("^Today is (\\d+)-(\\d+)-(\\d+)$")
    public void todayIs(int arg0, int arg1, int arg2) throws Throwable {
        throw new PendingException();
    }

    @When("^I call the conversion service with (\\d+) USD$")
    public void iCallTheConversionServiceWithUSD(int arg0) throws Throwable {
        throw new PendingException();
    }

    @When("^I call the conversion service without any additional information$")
    public void iCallTheConversionServiceWithoutAnyAdditionalInformation() throws Throwable {
        throw new PendingException();
    }

    @Then("^I should receive this information$")
    public void iShouldReceiveThisInformation() throws Throwable {
        throw new PendingException();
    }

    @And("^to-do$")
    public void toDo() throws Throwable {
        throw new PendingException();
    }

    @When("^I call the conversion service with (\\d+) USD and data before year (\\d+)$")
    public void iCallTheConversionServiceWithUSDAndDataBeforeYear(int arg0, int arg1) throws Throwable {
        throw new PendingException();
    }

    @Then("^I receive the  message Information is Available only from (\\d+)-(\\d+)-(\\d+)$")
    public void iReceiveTheMessageInformationIsAvailableOnlyFrom(int arg0, int arg1, int arg2) throws Throwable {
        throw new PendingException();
    }

    @Then("^I get the calculation as AnyValue CLP$")
    public void iGetTheCalculationAsAnyValueCLP() throws Throwable {
        throw new PendingException();
    }

    @Given("^I don't have a valid user$")
    public void iDonTHaveAValidUser() throws Throwable {
        authorizationToken = "invalidToken";
    }

    @Then("^I should receive a valid response$")
    public void iGetAValidResponse() throws Throwable {
        Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Then("^I should receive an invalid response$")
    public void thenIDonTGetAValidResponse() throws Throwable {
        Assert.assertNotEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @When("^I call the conversion service with (\\d+) (.*) to (.*) and date (.*)$")
    public void iCallTheConversionServiceWithUSDToEURAndDate(int amount, String from, String to, String date) throws Throwable {

        URI url = UriComponentsBuilder.newInstance().scheme("http")
                .host("localhost")
                .port(localPort)
                .path("/convert")
                .queryParam("from", from)
                .queryParam("amount", amount)
                .queryParam("to", to)
                .queryParam("date", date)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", authorizationToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
    }

    private String readFile(String filename) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(filename).toURI());
        StringBuilder data = new StringBuilder();
        Stream<String> lines = Files.lines(path);
        lines.forEach(line -> data.append(line).append("\n"));
        lines.close();
        return data.toString();
    }

    @Given("^I have a valid user$")
    public void iHaveAValidUser() {
        authorizationToken = "dummyValidToken";
    }
}
