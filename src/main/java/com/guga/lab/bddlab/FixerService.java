package com.guga.lab.bddlab;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
@Slf4j
public class FixerService {
    @Value("${fixer.host}")
    private String host;
    @Value("${fixer.port}")
    private int port;
    private String context = "/api/";
    private String API_KEY_VALUE = "005769388f0f40e17369dee738f22974";
    private String API_KEY_PARAM = "access_key";

    @Autowired
    private RestTemplate restTemplate;

    public double retrieveRate(double amount, String currency1, String currency2, String date) {
        if (date == null || currency1 == null || currency2 == null || amount == 0)
            throw new IllegalArgumentException("Not null values accepted in conversion routine");
        URI uri = UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host)
                .port(port)
                .path(context)
                .path(date)
                .queryParam(API_KEY_PARAM, API_KEY_VALUE)
                .build()
                .toUri();
        log.info(uri.toString());
        FxRates fxRates = restTemplate.getForEntity(uri, FxRates.class).getBody();
        log.info(fxRates.toString());
        return fxRates.getRates().get(currency2) / fxRates.getRates().get(currency1) * amount;
    }
}

