package com.guga.lab.bddlab;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class FxRates {

    @JsonProperty
    private String base;
    @JsonProperty
    private String date;
    @JsonProperty
    private Map<String, Double> rates;
}

