package com.itechart.deliveryservice.entity;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Contact {
	
    @Id
    @GeneratedValue
    private long id;
    
    @Basic(optional = false)
    private String name;
    
    @Basic(optional = false)
    private String surname;
    
    private String middleName;
    
    private Date dateOfBirth;  // TODO: change to Joda-time or other
    
    private String email;
    
    @Embedded
    private Address address;
    
    @OneToMany(mappedBy="owner")
    private List<Phone> phones;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

}
