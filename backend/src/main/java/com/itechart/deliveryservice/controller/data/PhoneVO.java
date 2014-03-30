package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.PhoneType;

public class PhoneVO {


    private long id;
    private long ownerId;
    private String countryCode;
    private String operatorCode;
    private String number;
    private PhoneType type;
    private String comment;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public long getOwnerId() {
        return ownerId;
    }
    
    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
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
