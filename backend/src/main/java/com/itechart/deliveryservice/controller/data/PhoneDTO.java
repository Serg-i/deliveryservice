package com.itechart.deliveryservice.controller.data;

import com.itechart.deliveryservice.entity.PhoneType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PhoneDTO {

    private long id;
    private long ownerId;
    @Size(max = 4)
    @Pattern(regexp = "^[0-9]+$")
    private String countryCode;
    @Size(max = 3)
    @Pattern(regexp = "^[0-9]+$")
    private String operatorCode;
    @Size(max = 8)
    @Pattern(regexp = "^[0-9]+$")
    private String number;
    private PhoneType type;
    @Size(max = 200)
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
    
    public void setType(PhoneType type) {
        this.type = type;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }

}
