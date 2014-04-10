package com.itechart.deliveryservice.controller.data;


import com.itechart.deliveryservice.controller.validators.Alphabetic;
import com.itechart.deliveryservice.controller.validators.Alphanumeric;
import com.itechart.deliveryservice.controller.validators.BasicString;
import com.itechart.deliveryservice.controller.validators.Email;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

public class ContactDTO {

    private long id;
    @Alphabetic
    @NotBlank(message = "hello")
    @BasicString
    private String name;
    @Alphabetic
    @NotBlank
    @BasicString
    private String surname;
    @Alphabetic
    @BasicString
    private String middleName;
    private Date dateOfBirth;
    @Email
    @BasicString
    private String email;
    @Alphabetic
    @BasicString
    private String city;
    @Alphanumeric
    @BasicString
    private String street;
    @Alphanumeric
    @BasicString
    private String flat;
    @Alphanumeric
    @BasicString
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
