package com.itechart.deliveryservice.entity;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @OneToOne(fetch=FetchType.LAZY, mappedBy="address")
    private Contact owner;
    
    private String city;
    private String street;
    private String home;
    private String flat;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

}
