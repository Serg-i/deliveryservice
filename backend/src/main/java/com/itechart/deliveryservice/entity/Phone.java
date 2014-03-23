package com.itechart.deliveryservice.entity;

import javax.persistence.*;

@Entity
public class Phone {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
  
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="owner_id")
	private Contact owner;
	
    @Column(name = "country_code", nullable = false)
    private String countryCode;
    
    @Column(name = "operator_code", nullable = false)
    private String operatorCode;
    
    @Column(nullable = false)
    private String number;
    
    @Enumerated(EnumType.STRING)
    private PhoneType type;
    
    private String comment;
    
    public long getId() {
    	return id;
    }
    
    public void setId(long id) {
    	this.id = id;
    }

    public Contact getOwner() {
    	return owner;
    }
    
    public void setOwner(Contact owner) {
    	this.owner = owner;
    }
    
    public String getCountryCode() {
    	return countryCode;
    }
    
    public void setCountryCode(String countryCode) {
    	this.countryCode = countryCode;
    }
    
    public String getOperatorCode() {
    	return operatorCode;
    }
    
    public void setOperatorCode(String operatorCode) {
    	this.operatorCode = operatorCode;
    }
    
    public String getNumber() {
    	return number;
    }
    
    public void setNumber(String number) {
    	this.number = number;
    }

    public PhoneType getType() {
    	return type;
    }
    
    public void setPhoneType(PhoneType type) {
    	this.type = type;
    }
    
    public String getComment() {
    	return comment;
    }
    
    public void setComment(String comment) {
    	this.comment = comment;
    }
    
}
