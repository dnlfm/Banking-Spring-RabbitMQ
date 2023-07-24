package com.dnlfm.domain;

import java.util.Date;
import java.util.List;

public class Account {

    private Long id; // to simplify, account number and id are the same
    private Customer customer;
    private Date createdAt;
    private String country; // more adequate to use some kind of Address object though... Will keep like this for simplicity. 'country' belongs to Account for international banking or multi-national banks.

    public Account() {}

    public Account(Long id) {
        this.id = id;
    }

    public Account(Customer customer, String country) {
        this.customer = customer;
        this.country = country;
    }

    public Account(Long id, Customer customer, Date createdAt, String country) {
        this.id = id;
        this.customer = customer;
        this.createdAt = createdAt;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
