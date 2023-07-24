package com.dnlfm.controller.dto;

import com.dnlfm.domain.CurrencyType;

import java.util.List;

public class CreateAccountRequest {
    private Long customerId;
    private String country;
    private List<CurrencyType> currencyTypes;

    public CreateAccountRequest() {}

    public CreateAccountRequest(Long customerId, String country, List<CurrencyType> currencyTypes) {
        this.customerId = customerId;
        this.country = country;
        this.currencyTypes = currencyTypes;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<CurrencyType> getCurrencyTypes() {
        return currencyTypes;
    }

    public void setCurrencyTypes(List<CurrencyType> currencyTypes) {
        this.currencyTypes = currencyTypes;
    }
}
