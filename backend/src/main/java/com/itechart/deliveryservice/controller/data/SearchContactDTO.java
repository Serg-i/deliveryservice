package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.utils.SearchParams;

import java.util.Date;

public class SearchContactDTO {

    private String name;
    private String surname;
    private String middleName;
    private Date dateOfBirth;
    private SearchParams.Operator dateOp;
    private String city;
    private String street;
    private String flat;
    private String home;

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

    public SearchParams.Operator getDateOp() {
        return dateOp;
    }

    public void setDateOp(SearchParams.Operator dateOp) {
        this.dateOp = dateOp;
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

    public SearchParams createParams() {

        SearchParams sp = new SearchParams();
        if (name != null && !name.isEmpty())
            sp.addParam("name", name);
        if (surname != null && !surname.isEmpty())
            sp.addParam("surname", surname);
        if (middleName != null && !middleName.isEmpty())
            sp.addParam("middleName", middleName);
        if (city != null && !city.isEmpty())
            sp.addParam("address.city", city);
        if (street != null && !street.isEmpty())
            sp.addParam("address.street", street);
        if (flat != null && !flat.isEmpty())
            sp.addParam("address.flat", flat);
        if (home != null && !home.isEmpty())
            sp.addParam("address.home", home);
        if (dateOfBirth != null && dateOp != null)
            sp.addParam("dateOfBirth", dateOp, dateOfBirth);
        return sp;
    }


}
