package com.dnlfm.domain;

import java.io.Serializable;

public class Customer implements Serializable {

    private Long id;
    private String name;
    private String email;

    public Customer() {}

    public Customer(Long id) {
        this.id = id;
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Customer(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
