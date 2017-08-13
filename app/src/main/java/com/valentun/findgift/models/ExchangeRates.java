package com.valentun.findgift.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRates {
    public String base;
    public Rates rates;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public class Rates {
        public double RUB;
        public double EUR;
        public double USD;
        public double GBP;
    }
}
