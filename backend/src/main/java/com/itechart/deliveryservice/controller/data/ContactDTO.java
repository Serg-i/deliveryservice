package com.itechart.deliveryservice.controller.data;


import java.util.Date;
import javax.validation.constraints.Pattern;

public class ContactDTO {
    private long id;

    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,30}")
    private String name;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,30}")
    private String surname;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,30}")
    private String middleName;

    private Date dateOfBirth;
    @Pattern(regexp = "^[_A-Za-z0-9-\\\\+]+(\\\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\\\.[A-Za-z0-9]+)*(\\\\.[A-Za-z]{2,})$")
    private String email;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,50}")
    private String city;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,50}")
    private String street;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,10}")
    private String flat;
    @Pattern(regexp = "[a-zA-Zа-яА-Я\\-]{1,10}")
    private String home;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }



}
