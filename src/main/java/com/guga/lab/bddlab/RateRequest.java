package com.guga.lab.bddlab;

import lombok.Data;
import lombok.NonNull;
import lombok.Setter;

@Data
@Setter
public class RateRequest {

    private String currency1;
    private String currency2;
    private double amount;
}
